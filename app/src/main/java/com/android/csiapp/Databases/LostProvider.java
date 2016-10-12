package com.android.csiapp.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 2016/10/12.
 */
public class LostProvider {
    // 表格名稱
    public static final String TABLE_NAME = "lost";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String ITEM_NAME_COLUMN = "item_name";
    public static final String ITEM_BRAND_COLUMN = "item_brand";
    public static final String ITEM_AMOUNT_COLUMN = "item_amount";
    public static final String ITEM_VALUE_COLUMN = "item_value";
    public static final String ITEM_FEATURE_COLUMN = "item_feature";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ITEM_NAME_COLUMN + " INTEGER NOT NULL, " +
                    ITEM_BRAND_COLUMN + " INTEGER NOT NULL, " +
                    ITEM_AMOUNT_COLUMN + " INTEGER NOT NULL, " +
                    ITEM_VALUE_COLUMN + " INTEGER NOT NULL, " +
                    ITEM_FEATURE_COLUMN + " INTEGER NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public LostProvider(Context context) {
        db = DatabasesHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public long insert(LostItem item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(ITEM_NAME_COLUMN, item.getItemName());
        cv.put(ITEM_BRAND_COLUMN, item.getItemBrand());
        cv.put(ITEM_AMOUNT_COLUMN, item.getItemAmount());
        cv.put(ITEM_VALUE_COLUMN, item.getItemValue());
        cv.put(ITEM_FEATURE_COLUMN, item.getItemFeature());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        return id;
    }

    // 修改參數指定的物件
    public boolean update(long id, LostItem item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(ITEM_NAME_COLUMN, item.getItemName());
        cv.put(ITEM_BRAND_COLUMN, item.getItemBrand());
        cv.put(ITEM_AMOUNT_COLUMN, item.getItemAmount());
        cv.put(ITEM_VALUE_COLUMN, item.getItemValue());
        cv.put(ITEM_FEATURE_COLUMN, item.getItemFeature());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + id;

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }
}
