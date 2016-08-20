package gui.lwjgl.components;

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
	
	public Texture(int texID) {
		this.texID = texID;
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texID);
	}

	public int getID() {
		return texID;
	}
	
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

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}