#mysqldb    
import time, MySQLdb
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

def readUserprofileOne(id):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    sql = "select * from userprofile where userid = %s"
    param = id
    n = cursor.execute(sql,param)
    res = cursor.fetchall()

    cursor.close()
    conn.close()
    return res

def updateUserprofilePassward(id,pwd):
    conn=MySQLdb.connect(host="localhost",user="root",passwd="123456",db="tabiagentdb")
    cursor = conn.cursor()

    sql = "update userprofile set passward = %s where userid = %s"
    param = (pwd,id)
    n = cursor.execute(sql,param)
    res = cursor.fetchall()

    cursor.close()
    conn.close()
    return res
