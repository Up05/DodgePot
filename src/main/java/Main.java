import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

    private static int width = 600, height = 400;
    private static long window;

    public static final double GRAVITY = 0.03;

    public static boolean imagesEnabled = false;
    public static Main instance = null;

    // Oh the wonders of making your own crap crappier because you can't be bothered to go through docs...
    public static HashSet<Integer> activeKeys = new HashSet<>();

    Player player = new Player();

    public static int frameCount;

    public void run() {
        instance = this;
        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, "DodgePot", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();
        glClearColor(0.05f, 0.05f, 0.05f, 0.0f);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glOrtho(0, width, height, 0, 1.0, -1.0); // p5 proj mat thing yeap

        TextFast.init();
//        String out = "_error";
//        try {
//            out = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("pot0.txt").toURI())));
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//        TextFast pot = new TextFast(out, 300, 200).setCentered(true).setCenteredY(true).setSpaceBetweenChars(10);

        EnemySpawner es = new EnemySpawner(); // ! ...

        while ( !glfwWindowShouldClose(window) ) {

            {
                IntBuffer pWidth  = BufferUtils.createIntBuffer(1); // int*
                IntBuffer pHeight = BufferUtils.createIntBuffer(1); // int*
                glfwGetWindowSize(window, pWidth, pHeight);
                if(width != pWidth.get(0)) {
                    width = pWidth.get(0);
                    height = pHeight.get(0);
                    glViewport(0, 0, width, height);
                    glLoadIdentity();
                    glOrtho(0, width, height, 0, 1.0, -1.0); // p5 proj mat thing yeap
                    glMatrixMode(GL11.GL_MODELVIEW);
                }
            }
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Shapes.line(0, height / 2, width, height / 2);
            Shapes.line(width / 2, 0, width / 2, height);

            player.update().show();
//            pot.setPosition(player.getPosition().add(0, -40));
            es.spawn().update();

            glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {

                if(action == GLFW_PRESS)
                    activeKeys.add(key);
                else if(action == GLFW_RELEASE)
                    activeKeys.remove(key);


            });

//            glfwSetMouseButtonCallback(window, (long window, int button, int action, int mods) -> {
//                if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE)
//                    player.shoot();
//            });


            frameCount ++;
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public static Vector2 getMousePos(){
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        glfwGetCursorPos(window, xpos, ypos);

        return new Vector2(xpos[0], ypos[0]);
    }

    public static int getWidth(){
        return width;
    }

    public static int getHeight(){
        return height;
    }

    public Player getPlayer() {
        return player;
    }
}
