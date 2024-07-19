import static org.lwjgl.opengl.GL11.*;

public class Shapes {
    public static void rect(int x, int y, int w, int h){
        final int x2 = x + w, y2 = y + h;
        glBegin(GL_QUADS);
        glVertex2i(x , y );
        glVertex2i(x2, y );
        glVertex2i(x2, y2);
        glVertex2i(x , y2);
        glEnd();
    }

    public static void ellipse(int x, int y, int r, int res) {
        final int vertN = res + 1;
        final double TWO_PI = Math.PI * 2d;

        Integer[] verts = new Integer[vertN * 2];

        for (int i = 1; i < vertN; i ++){
            verts[i * 2]     = (int) Math.round(x + ( r * Math.cos( i * TWO_PI / res ) ));
            verts[i * 2 + 1] = (int) Math.round(y + ( r * Math.sin( i * TWO_PI / res ) ));
        }

        glPushMatrix();
        glBegin(GL_TRIANGLE_FAN);
        for(int i = 0; i < verts.length; i += 2)
            if(verts[i] != null && verts[i + 1] != null)
                glVertex2d(verts[i], verts[i + 1]);
        glEnd();
        glPopMatrix();
    }
    public static void ellipse(Vector2 vector, int r, int res) {
        int x = (int) vector.getX(), y = (int) vector.getY();

        final int vertN = res + 1;
        final double TWO_PI = Math.PI * 2d;

        Integer[] verts = new Integer[vertN * 2];

        for (int i = 1; i < vertN; i ++){
            verts[i * 2]     = (int) Math.round(x + ( r * Math.cos( i * TWO_PI / res ) ));
            verts[i * 2 + 1] = (int) Math.round(y + ( r * Math.sin( i * TWO_PI / res ) ));
        }

        glPushMatrix();
        glBegin(GL_TRIANGLE_FAN);
        for(int i = 0; i < verts.length; i += 2)
            if(verts[i] != null && verts[i + 1] != null)
                glVertex2d(verts[i], verts[i + 1]);
        glEnd();
        glPopMatrix();
    }

    public static void line(int x, int y, int x2, int y2){
        glBegin(GL_LINE_STRIP);
        glVertex2i(x, y);
        glVertex2i(x2, y2);
        glEnd();
    }
    public static void line(Vector2 vec1, Vector2 vec2){
        glBegin(GL_LINE_STRIP);
        vec1.vertex();
        vec2.vertex();
        glEnd();
    }

    public static void point(Vector2 pos){
        ellipse((int) pos.getX(), (int) pos.getY(), 15, 5);
    }

    public static void character(int x, int y, int w, int h, double tx, double ty, double tw, double th, int textureId){
        if(!Main.imagesEnabled){
            glEnable(GL_TEXTURE_2D);
            Main.imagesEnabled = true;
        }

        final int x2 = x + w, y2 = y + h;
        final double tx2 = tx + tw, ty2 = ty + th;

        glBindTexture(GL_TEXTURE_2D, textureId);
        glBegin(GL_QUADS);
        glTexCoord2d(tx, ty);
        glVertex2i(x , y );
        glTexCoord2d(tx2, ty);
        glVertex2i(x2, y );
        glTexCoord2d(tx2, ty2);
        glVertex2i(x2, y2);
        glTexCoord2d(tx, ty2);
        glVertex2i(x , y2);
        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
