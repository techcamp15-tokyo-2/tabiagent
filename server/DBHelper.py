#mysqldb
import os
import time, MySQLdb

#this fun help load all records in userprofile table
def readUserprofileall():
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    n = cursor.execute("select * from userprofile")
    res = cursor.fetchall()
    #for row in cursor.fetchall():
        #    for r in row:
     #   print row

    cursor.close()
    conn.close()
    return res

#load one record in userprofile table
def readUserprofileOnebyID(id):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    sql = "select * from userprofile where userid = %s"
    param = id
    n = cursor.execute(sql,param)
    res = cursor.fetchall()

    cursor.close()
    conn.close()
    return res

#load one record in userprofile table
def readUserprofileOnebyEmailPwd(email,pwd):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    sql = "select * from userprofile where email = %s and passward = %s"
    param = (email,pwd)
    n = cursor.execute(sql,param)
    res = cursor.fetchall()

    conn.commit()
    cursor.close()
    conn.close()
    return res

#revise item in userprofile table
def updateUserprofilePassward(id,pwd):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    sql = "update userprofile set passward = %s where userid = %s"
    param = (pwd,id)
    n = cursor.execute(sql,param)

    conn.commit()
    cursor.close()
    conn.close()

#insert one record
def insertUserprofile(item):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()
    sql = "insert into userprofile values(null,%s,%s,%s,%s,%s,%s,%s,%s)"
    param = (item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7])
    n = cursor.execute(sql,param)

    conn.commit()
    cursor.close()
    conn.close()
    return n

def load_file(path):
    file_object = open('path')
    try:
        all_the_text = file_object.read( )
    finally:
        file_object.close( )

#load all records in pictures table
def readPictureTop10(start):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    sql ="select * from pictures order by uploadtime desc limit %s,10"
    param = start*10
    n = cursor.execute(sql,param)
    res = cursor.fetchall()

    conn.commit()
    cursor.close()
    conn.close()
    return res

#load all records in pictures table
def readFriendsAll(userid):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    sql ="select * from userfriends where userid = %s"
    param = userid
    n = cursor.execute(sql,param)

    res = cursor.fetchall()
    conn.commit()
    cursor.close()
    conn.close()
    return res

#load all records in pictures table
def readPicturesbyID(userid):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    sql ="select * from pictures where userid = %s"
    param = userid
    n = cursor.execute(sql,param)

    res = cursor.fetchall()
    conn.commit()
    cursor.close()
    conn.close()
    return res
