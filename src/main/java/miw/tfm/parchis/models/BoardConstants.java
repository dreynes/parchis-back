package miw.tfm.parchis.models;

import java.util.Arrays;
import java.util.List;

public class BoardConstants {

    public static final List<Integer> SQUARE_CIRCUIT = Arrays.asList(170, 169, 168, 167, 165, 164, 163, 180, 197, 231, 248, 265, 282,
            280, 263, 246, 229, 195, 178, 161, 160, 159, 157, 156, 155, 154, 137, 120, 121, 122, 123, 125, 126, 127, 110,
            93, 59, 42, 25, 8, 10, 27, 44, 61, 95, 112, 129, 130, 131, 133, 134, 135, 136);

    public static final List<Integer> PATH_GREEN = Arrays.asList( 78, 95, 112, 129, 130, 131, 132, 133, 134, 135, 136, 153, 170, 169, 168,
            167, 166, 165, 164,163, 180, 197, 214, 231, 248, 265, 282, 281, 280, 263, 246, 229, 212, 195, 178, 161, 160, 159, 158, 157, 156,
            155, 154, 137, 120, 121, 122, 123, 124, 125, 126, 127, 110, 93, 76, 59, 42, 25, 8, 9);
    public static final List<Integer> PATH_BLUE = Arrays.asList( 212, 195, 178, 161, 160, 159, 158, 157, 156, 155, 154, 137, 120, 121,
            122, 123, 124, 125, 126, 127, 110, 93, 76, 59, 42, 25, 8, 9, 10, 27, 44, 61, 78, 95, 112, 129, 130, 131, 132, 133, 134,
            135, 136, 153, 170, 169, 168, 167, 166, 165, 164, 163, 180, 197, 214, 231, 248, 265, 282, 281);

    public static final List<Integer> PATH_RED = Arrays.asList( 124, 125, 126, 127, 110, 93, 76, 59, 42, 25, 8, 9, 10, 27, 44, 61, 78, 95,
            112, 129, 130, 131, 132, 133, 134, 135, 136, 153, 170, 169, 168, 167, 166, 165, 164,163, 180, 197, 214, 231, 248, 265,
            282, 281, 280, 263, 246, 229, 212, 195, 178, 161, 160, 159, 158, 157, 156, 155, 154,137);

    public static final List<Integer> PATH_YELLOW = Arrays.asList(166, 165, 164,163, 180, 197, 214, 231, 248, 265, 282, 281, 280, 263, 246, 229, 212, 195, 178, 161,
            160, 159, 158, 157, 156, 155, 154, 137, 120, 121, 122, 123, 124, 125, 126, 127, 110, 93, 76, 59, 42, 25, 8, 9, 10,
            27, 44, 61, 78, 95, 112, 129, 130, 131, 132, 133, 134, 135, 136, 153);
    public static final List<List<Integer>> PATHS= Arrays.asList(PATH_RED, PATH_BLUE,PATH_YELLOW, PATH_GREEN);
    public static final List<String> COLORS = Arrays.asList("red", "blue", "yellow", "green");
    public static final List<Integer> SQUARE_SAFE_VALUES = Arrays.asList(9, 76, 132, 137, 153,158, 214, 281);
    public static final List<Integer> SQUARE_EXIT = Arrays.asList(124, 212, 166, 78);
    public static final List<Integer> SQUARE_FINAL_TRACK_VALUES_YELLOW = Arrays.asList(152, 151, 150 ,149 ,148, 147, 146);
    public static final List<Integer> SQUARE_FINAL_TRACK_VALUES_RED = Arrays.asList(138, 139, 140, 141, 142, 143, 144);
    public static final List<Integer> SQUARE_FINAL_TRACK_VALUES_BLUE = Arrays.asList(264, 247, 230, 213, 196, 179, 162);
    public static final List<Integer> SQUARE_FINAL_TRACK_VALUES_GREEN = Arrays.asList(26, 43, 60, 77, 94, 111, 128);
    public static final List<Integer> SQUARE_HOME_VALUES_RED = Arrays.asList(19, 20, 21, 22, 23, 36, 37, 38, 39, 40, 53, 54, 55, 56, 57, 70, 71, 72, 73, 74, 87, 88, 89, 90, 91);
    public static final List<Integer> SQUARE_HOME_VALUES_GREEN = Arrays.asList(29, 30, 31, 32, 33, 46, 47, 48, 49, 50, 63, 64, 65, 66, 67, 80, 81, 82, 83, 84, 97, 98, 99, 100, 101);
    public static final List<Integer> SQUARE_HOME_VALUES_BLUE = Arrays.asList(189, 190, 191, 192, 193, 206, 207, 208, 209, 210, 223, 224, 225, 226, 227, 240, 241, 242, 243, 244, 257, 258, 259, 260, 261);
    public static final List<Integer> SQUARE_HOME_VALUES_YELLOW = Arrays.asList(199, 200, 201, 202, 203, 216, 217, 218, 219, 220, 233, 234, 235, 236, 237, 250, 251, 252, 253, 254, 267, 268, 269, 270, 271);

}
