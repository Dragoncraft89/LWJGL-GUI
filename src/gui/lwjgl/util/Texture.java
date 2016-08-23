package gui.lwjgl.util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import java.awt.Graphics;

/**
 * This class converts Images into a for lwjgl usable format
 * @author Florian
 *
 */
public final class Texture {
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
    private static ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[] {8,8,8,8},
            true,
            false,
            ComponentColorModel.TRANSLUCENT,
            DataBuffer.TYPE_BYTE);

    private static ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[] {8,8,8,0},
            false,
            false,
            ComponentColorModel.OPAQUE,
            DataBuffer.TYPE_BYTE);
	
	private int texID;
	private int width;
	private int height;
	
	/**
	 * Constructor
	 * @param texID
	 */
	public Texture(int texID) {
		this.texID = texID;
	}
	
	/**
	 * Binds the texture so it can be used for rendering
	 */
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texID);
	}

	/**
	 * Returns the texture ID
	 * @return the texture ID
	 */
	public int getID() {
		return texID;
	}
	
	/**
	 * Creates a Texture-Object from a {@link BufferedImage} object
	 * @param image
	 * @return
	 */
	public static Texture createTextureFromImage(BufferedImage image) {
        int srcPixelFormat;
        
        int textureID = glGenTextures();
        Texture tex = new Texture(textureID);
        tex.width = image.getWidth();
        tex.height = image.getHeight();
        glBindTexture(GL_TEXTURE_2D, textureID);
		if (image.getColorModel().hasAlpha()) {
            srcPixelFormat = GL_RGBA;
        } else {
            srcPixelFormat = GL_RGB;
        }
 
        ByteBuffer textureBuffer = convertImageData(image, tex);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D,
                      0,
                      GL_RGBA,
                      get2Fold(image.getWidth()),
                      get2Fold(image.getHeight()),
                      0,
                      srcPixelFormat,
                      GL_UNSIGNED_BYTE,
                      textureBuffer );
        return tex;

	}
	
	/**
	 * Loads a Texture from the given file
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Texture createTextureFromFile(String filename) throws IOException {
		Texture tex = textures.get(filename);
		 
        if (tex != null) {
            return tex;
        }
        BufferedImage bufferedImage = ImageIO.read(new File(filename));
 
        tex = createTextureFromImage(bufferedImage);
 
        textures.put(filename,tex);
 
        return tex;
	}

	/**
	 * Loads the Texture from a given packageName<br>
	 * <b>Note:</b> The last part from the packageName is interpreted as the file extension, e.g. for a.b.image.png is the equivalent path a/b/image.png
	 * @param packageName
	 * @return
	 * @throws IOException
	 */
	public static Texture createTextureFromPackage(String packageName) throws IOException {
		Texture tex = textures.get(packageName);
		
		if (tex != null) {
            return tex;
        }
		String extension = packageName.substring(packageName.lastIndexOf('.'));
		packageName = packageName.substring(0, packageName.lastIndexOf('.'));
		String path = "/" + packageName.replace('.', '/') + extension;
		
        BufferedImage bufferedImage = ImageIO.read(Texture.class.getResourceAsStream(path));
 
        tex = createTextureFromImage(bufferedImage);
 
        textures.put(packageName,tex);
 
        return tex;
	}
	
	private static int get2Fold(int fold) {
        int ret = 2;
        while (ret < fold) {
            ret *= 2;
        }
        return ret;
    }

    @SuppressWarnings("rawtypes")
	private static ByteBuffer convertImageData(BufferedImage bufferedImage,Texture texture) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;
        int width = get2Fold(bufferedImage.getWidth());
        int height = get2Fold(bufferedImage.getHeight());
        if (bufferedImage.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,width,height,4,null);
            texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,width,height,3,null);
            texImage = new BufferedImage(glColorModel,raster,false,new Hashtable());
        }
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f,0f,0f,0f));
        g.fillRect(0, 0, texImage.getWidth(), texImage.getHeight());
        g.drawImage(bufferedImage,0,0,texImage.getWidth(),texImage.getHeight(),null);

        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
 
        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();
 
        return imageBuffer;
    }

    /**
     * Returns the Textures width
     * @return the width
     */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the Textures height
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
}
