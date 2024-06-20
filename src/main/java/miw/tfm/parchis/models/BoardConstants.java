package miw.tfm.parchis.models;

import java.util.Arrays;
import java.util.List;

public class BoardConstants {
    public static final List<String> COLORS = Arrays.asList("red", "blue","yellow","green");
    public static final List<Integer> SQUARE_SAFE_VALUES = Arrays.asList(9, 76, 132, 137, 153, 214, 281);
    public static final List<Integer> SQUARE_EXIT_GREEN = Arrays.asList(78);
    public static final List<Integer> SQUARE_EXIT_RED = Arrays.asList(124);
    public static final List<Integer> SQUARE_EXIT_BLUE = Arrays.asList(212);
    public static final List<Integer> SQUARE_EXIT_YELLOW = Arrays.asList(166);
    public static final List<Integer> SQUARE_FINAL_TRACK_VALUES_YELLOW = Arrays.asList(146, 147, 148, 149, 150, 151, 152);
    public static final List<Integer> SQUARE_FINAL_TRACK_VALUES_RED = Arrays.asList(138, 139, 140, 141, 142, 143, 144);
    public static final List<Integer> SQUARE_FINAL_TRACK_VALUES_BLUE = Arrays.asList(162, 179, 196, 213, 230, 230, 247, 264);
    public static final List<Integer> SQUARE_FINAL_TRACK_VALUES_GREEN = Arrays.asList(26, 43, 60, 77, 94, 111, 128);
    public static final List<Integer> SQUARE_HOME_VALUES_RED = Arrays.asList(19, 20, 21, 22, 23, 36, 37, 38, 39, 40, 53, 54, 55, 56, 57, 70, 71, 72, 73, 74, 87, 88, 89, 90, 91);
    public static final List<Integer> SQUARE_HOME_VALUES_GREEN = Arrays.asList(29, 30, 31, 32, 33, 46, 47, 48, 49, 50, 63, 64, 65, 66, 67, 80, 81, 82, 83, 84, 97, 98, 99, 100, 101);
    public static final List<Integer> SQUARE_HOME_VALUES_BLUE = Arrays.asList(189, 190, 191, 192, 193, 206, 207, 208, 209, 210, 223, 224, 225, 226, 227, 240, 241, 242, 243, 244, 257, 258, 259, 260, 261);
    public static final List<Integer> SQUARE_HOME_VALUES_YELLOW = Arrays.asList(199, 200, 201, 202, 203, 216, 217, 218, 219, 220, 233, 234, 235, 236, 237, 250, 251, 252, 253, 254, 267, 268, 269, 270, 271);
    public static final List<Integer> SQUARE_BLACK_BORDER = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 18, 24, 35, 41, 52, 58, 69, 75, 86, 92, 103, 104, 105, 106, 107, 108, 109,
            11, 12, 13, 14, 15, 16, 17, 28, 34, 45, 51, 62, 68, 79, 85, 96, 102, 113, 114, 115, 116, 117, 118, 119,
            181, 182, 183, 184, 185, 186, 187, 198, 204, 215, 221, 232, 238, 249, 255, 266, 272, 283, 284, 285, 286, 287, 288, 289,
            171, 172, 173, 174, 175, 176, 177, 188, 194, 205, 211, 222, 228, 239, 245, 256, 262, 273, 274, 275, 275, 276, 277, 278, 279);

}