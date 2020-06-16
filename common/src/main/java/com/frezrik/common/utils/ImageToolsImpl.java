package com.frezrik.common.utils;

import android.graphics.*;
import android.util.Log;
import com.frezrik.core.api.ImageTools;
import com.frezrik.router.annotation.ApiImpl;

import java.util.Random;

@ApiImpl
public class ImageToolsImpl implements ImageTools {

    private static final String TAG = "zmzm";

    @Override
    public Bitmap binarization(Bitmap img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int area = width * height;
        int gray[][] = new int[width][height];
        int average = 0;// 灰度平均值
        int graysum = 0;
        int graymean = 0;
        int grayfrontmean = 0;
        int graybackmean = 0;
        int pixelGray;
        int front = 0;
        int back = 0;
        int[] pix = new int[width * height];
        img.getPixels(pix, 0, width, 0, 0, width, height);
        for (int i = 1; i < width; i++) { // 不算边界行和列，为避免越界
            for (int j = 1; j < height; j++) {
                int x = j * width + i;
                int r = (pix[x] >> 16) & 0xff;
                int g = (pix[x] >> 8) & 0xff;
                int b = pix[x] & 0xff;
                pixelGray = (int) (0.3 * r + 0.59 * g + 0.11 * b);// 计算每个坐标点的灰度
                gray[i][j] = (pixelGray << 16) + (pixelGray << 8) + (pixelGray);
                graysum += pixelGray;
            }
        }
        graymean = (int) (graysum / area);// 整个图的灰度平均值
        average = graymean;
        Log.i(TAG, "Average:" + average);
        for (int i = 0; i < width; i++) // 计算整个图的二值化阈值
        {
            for (int j = 0; j < height; j++) {
                if (((gray[i][j]) & (0x0000ff)) < graymean) {
                    graybackmean += ((gray[i][j]) & (0x0000ff));
                    back++;
                } else {
                    grayfrontmean += ((gray[i][j]) & (0x0000ff));
                    front++;
                }
            }
        }
        int frontValue = (int) (grayfrontmean / front);// 前景中心
        int backValue = (int) (graybackmean / back);// 背景中心
        float G[] = new float[frontValue - backValue + 1];// 方差数组
        int s = 0;
        Log.i(TAG, "Front:" + front + "**Frontvalue:" + frontValue + "**Backvalue:" + backValue);
        for (int i1 = backValue; i1 < frontValue + 1; i1++)// 以前景中心和背景中心为区间采用大津法算法（OTSU算法）
        {
            back = 0;
            front = 0;
            grayfrontmean = 0;
            graybackmean = 0;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (((gray[i][j]) & (0x0000ff)) < (i1 + 1)) {
                        graybackmean += ((gray[i][j]) & (0x0000ff));
                        back++;
                    } else {
                        grayfrontmean += ((gray[i][j]) & (0x0000ff));
                        front++;
                    }
                }
            }
            grayfrontmean = (int) (grayfrontmean / front);
            graybackmean = (int) (graybackmean / back);
            G[s] = (((float) back / area) * (graybackmean - average)
                    * (graybackmean - average) + ((float) front / area)
                    * (grayfrontmean - average) * (grayfrontmean - average));
            s++;
        }
        float max = G[0];
        int index = 0;
        for (int i = 1; i < frontValue - backValue + 1; i++) {
            if (max < G[i]) {
                max = G[i];
                index = i;
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int in = j * width + i;
                if (((gray[i][j]) & (0x0000ff)) < (index + backValue)) {
                    pix[in] = Color.rgb(0, 0, 0);
                } else {
                    pix[in] = Color.rgb(255, 255, 255);
                }
            }
        }

        Bitmap temp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        temp.setPixels(pix, 0, width, 0, 0, width, height);

        return temp;
    }

    /**
     * 灰化
     *
     * @param bitmap
     * @return
     */
    public static Bitmap setGrey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }

    /**
     * 转为二值图像
     *
     * @param bmp           原图bitmap
     * @param grayThreshold 二值化的域值
     * @return Bitmap
     */
    public static Bitmap setBMW(Bitmap bmp, int grayThreshold) {
        int index = 0, grey = 0, red = 0, green = 0, blue = 0;
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index = width * y + x;
                grey = pixels[index];

                red = Color.red(grey) > grayThreshold ? 255 : 0;
                green = Color.green(grey) > grayThreshold ? 255 : 0;
                blue = Color.blue(grey) > grayThreshold ? 255 : 0;

                pixels[index] = Color.rgb(red, green, blue);
                if (pixels[index] == Color.WHITE) {
                    pixels[index] = Color.WHITE;
                } else {
                    pixels[index] = Color.BLACK;
                }
            }
        }
        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }

    public static Bitmap setEmboss(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap bmpReturn = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int preColor = 0;
        int prepreColor =  bitmap.getPixel(0, 0);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int curr_color = bitmap.getPixel(i, j);
                int r = Color.red(curr_color) - Color.red(prepreColor) + 128;
                int g = Color.green(curr_color) - Color.red(prepreColor) + 128;
                int b = Color.green(curr_color) - Color.blue(prepreColor) + 128;
                int a = Color.alpha(curr_color);
                int modif_color = Color.argb(a, r, g, b);
                bmpReturn.setPixel(i, j, modif_color);
                prepreColor = preColor;
                preColor = curr_color;
            }
        }

        Canvas c = new Canvas(bmpReturn);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpReturn, 0, 0, paint);

        return bmpReturn;
    }

    /* 设置图片模糊 */
    public static Bitmap toMohu(Bitmap bmpSource, int Blur)  //源位图，模糊强度
    {
        Bitmap bmpReturn = Bitmap.createBitmap(bmpSource.getWidth(), bmpSource.getHeight(), Bitmap.Config.ARGB_8888);
        int pixels[] = new int[bmpSource.getWidth() * bmpSource.getHeight()];
        int pixelsRawSource[] = new int[bmpSource.getWidth() * bmpSource.getHeight() * 3];
        int pixelsRawNew[] = new int[bmpSource.getWidth() * bmpSource.getHeight() * 3];

        bmpSource.getPixels(pixels, 0, bmpSource.getWidth(), 0, 0, bmpSource.getWidth(), bmpSource.getHeight());

        for (int k = 1; k <= Blur; k++) {
            //从图片中获取每个像素三原色的值
            for (int i = 0; i < pixels.length; i++) {
                pixelsRawSource[i * 3 + 0] = Color.red(pixels[i]);
                pixelsRawSource[i * 3 + 1] = Color.green(pixels[i]);
                pixelsRawSource[i * 3 + 2] = Color.blue(pixels[i]);
            }
            //取每个点上下左右点的平均值作自己的值
            int CurrentPixel = bmpSource.getWidth() * 3 + 3;
            // 当前处理的像素点，从点(2,2)开始
            for (int i = 0; i < bmpSource.getHeight() - 3; i++) // 高度循环
            {
                for (int j = 0; j < bmpSource.getWidth() * 3; j++) // 宽度循环
                {
                    CurrentPixel += 1;                 // 取上下左右，取平均值
                    int sumColor = 0; // 颜色和
                    sumColor = pixelsRawSource[CurrentPixel - bmpSource.getWidth() * 3]; // 上一点
                    sumColor = sumColor + pixelsRawSource[CurrentPixel - 3]; // 左一点
                    sumColor = sumColor + pixelsRawSource[CurrentPixel + 3]; // 右一点
                    sumColor = sumColor + pixelsRawSource[CurrentPixel + bmpSource.getWidth() * 3]; // 下一点
                    pixelsRawNew[CurrentPixel] = Math.round(sumColor / 4); // 设置像素点
                }
            }

            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = Color.rgb(pixelsRawNew[i * 3 + 0], pixelsRawNew[i * 3 + 1], pixelsRawNew[i * 3 + 2]);
            }
        }

        bmpReturn.setPixels(pixels, 0, bmpSource.getWidth(), 0, 0, bmpSource.getWidth(), bmpSource.getHeight());  //必须新建位图然后填充，不能直接填充源图像，否则内存报错
        return bmpReturn;
    }

    /* 设置图片积木效果 */
    public static Bitmap toJiMu(Bitmap mBitmap) {
        int mBitmapWidth = 0;
        int mBitmapHeight = 0;

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        Bitmap bmpReturn = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight, Bitmap.Config.ARGB_8888);

        int iPixel = 0;
        for (int i = 0; i < mBitmapWidth; i++) {
            for (int j = 0; j < mBitmapHeight; j++) {
                int curr_color = mBitmap.getPixel(i, j);

                int avg = (Color.red(curr_color) + Color.green(curr_color) + Color.blue(curr_color)) / 3;
                if (avg >= 100) {
                    iPixel = 255;
                } else {
                    iPixel = 0;
                }
                int modif_color = Color.argb(255, iPixel, iPixel, iPixel);

                bmpReturn.setPixel(i, j, modif_color);
            }
        }

        return bmpReturn;
    }

    /* 设置油画 */
    public static Bitmap toYouHua(Bitmap bmpSource)  //源位图
    {
        Bitmap bmpReturn = Bitmap.createBitmap(bmpSource.getWidth(), bmpSource.getHeight(), Bitmap.Config.ARGB_8888);
        int color = 0;
        int Radio = 0;
        int width = bmpSource.getWidth();
        int height = bmpSource.getHeight();

        Random rnd = new Random();
        int iModel = 10;
        int i = width - iModel;
        while (i > 1) {
            int j = height - iModel;
            while (j > 1) {
                int iPos = rnd.nextInt(100000) % iModel;
                //将半径之内取任一点
                color = bmpSource.getPixel(i + iPos, j + iPos);
                bmpReturn.setPixel(i, j, color);
                j = j - 1;
            }
            i = i - 1;
        }
        return bmpReturn;
    }
}
