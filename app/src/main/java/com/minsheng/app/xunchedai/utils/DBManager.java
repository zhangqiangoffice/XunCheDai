package com.minsheng.app.xunchedai.utils;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.CursorUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;
import com.minsheng.app.xunchedai.loan.bean.Loan;
import com.minsheng.app.xunchedai.message.bean.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 * 数据库管理类 對表的增刪改查
 */
public class DBManager {
    //创建数据库
    public static DbUtils dbUtils(Context context) {
        DbUtils db = null;
        db = DbUtils.create(context, "mstps");
        db.configAllowTransaction(true);
        db.configDebug(true);
        return db;
    }

    //数据库序列的值
    public static CursorUtils cursorUtils()
    {
        CursorUtils cursorUtils=null;
        cursorUtils=new CursorUtils();
        return  cursorUtils;
    }

    public static DbModel getDBModel(Cursor cursor)
    {
        DbModel dbModel=null;
        dbModel=new DbModel();
        return  dbModel;
    }

    //新建表的方法
    public static void createTable(DbUtils db)
    {
        try {
            db.createTableIfNotExist(Message.class);
            db.createTableIfNotExist(Loan.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //新建表并且保存相关信息
    public static void saveMsg(DbUtils db, List<Message> messages) {
        try {
            db.saveAll(messages);
            Log.i("123", "保存成功");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //查询表格集合
    public static List<Message> getMsgs(DbUtils db) {
        List<Message> users = null;
        try {
            users = db.findAll(Selector.from(Message.class));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return users;

    }

    //查询某一个员工
    public static Message getMsg(DbUtils db, String staffid) {
        Message user = null;
        try {
            user = db.findFirst(Selector.from(Message.class).where("staffid", "=", staffid));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return user;
    }

    //修改某一个员工的信息
    public static void updateMsg(DbUtils db, Message user) {
        try {
            db.saveOrUpdate(user);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    //删除某条记录
    public static void deleteMsg(DbUtils db,String id)
    {
        try {
            db.createTableIfNotExist(Message.class);
            db.delete(Message.class, WhereBuilder.b("id", "=", id));
            Log.i("123", "刪除成功");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //新建表并且保存相关贷款申请信息
    public static void saveLoan(DbUtils db,Loan loan) {
        try {
            db.saveOrUpdate(loan);
            Log.i("123", "保存贷款信息成功");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //查询贷款申请表格集合
    public static List<Loan> getLoans(DbUtils db) {
        List<Loan> loans = null;
        try {
            loans = db.findAll(Selector.from(Loan.class).orderBy("edit_date",true));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return loans;

    }

    //删除某条贷款申请记录
    public static void deleteLoan(DbUtils db,Loan loan)
    {
        try {
            db.delete(loan);
            Log.i("123", "刪除成功");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //获得空值
    public static String percent(Loan loan)
    {
        int count=0;
        List<String>strList=new ArrayList<>();
        strList.add(loan.getName());
        strList.add(loan.getPhone());
        strList.add(loan.getLoan_amount());
        strList.add(loan.getProtection_date());
        strList.add(loan.getInstallment());
        strList.add(loan.getLoan_object());
        strList.add(loan.getInsurance_amount());
        strList.add(loan.getWarranty());
        strList.add(loan.getInvoice());
        strList.add(loan.getCare_card());
        strList.add(loan.getIdcard1());
        strList.add(loan.getIdcard2());
        strList.add(loan.getBankcard1());
        strList.add(loan.getBankcard2());
        strList.add(loan.getLicense());
        strList.add(loan.getAgreement_apply());
        strList.add(loan.getAgreement_entrust());
        strList.add(loan.getAgreement_transfer());

        for (int i=0;i<strList.size();i++)
        {
            if (TextUtils.isEmpty(strList.get(i)))
            {
                count++;
            }
        }
        int size = strList.size();
        String result = String.format("%.2f", (float) (size - count) / (float) size * 100);
        return result;

    }

}
