__author__ = 'Wei'
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import json
import time
import math

from DBHelper import *
from BaseHandler import *
from tornado.options import define,options

class abc(tornado.web.RequestHandler):
    def get(self, *args, **kwargs):
        self.write('''
<html><body><form action="/referInf" method="post" >
                 <input type="text" name="referInf">
                   <input type="submit" value="Submit">
                   </form></body></html>''')

class logout(BaseHandler):
    def get(self):
        self.redirect("/login")


class login(BaseHandler):
    #def post(self, *args, **kwargs):
     #   information=self.get_arguments("referInf")
      #  infGet=json.loads(information[0])
    def get(self):
        print 'log in info:'
        if self.get_arguments("email"):
            email=self.get_argument("email")
            pwd=self.get_argument("pwd")
            res = readUserprofileOnebyEmailPwd(email,pwd)
            if res:
                self.set_secure_cookie("userid",str(res[0][0]))
                print "login success"
                response=[]
                response.append({"userid":str(res[0][0]),"state":"1","name":res[0][2]})
                self.write(json.dumps(response))
            else:
                print 'login failed!'
                response=[]
                response.append({"state":"0"})
                self.write(json.dumps(response))
                #self.redirect("/login")
        else:
            self.write("please input your account")

class register(BaseHandler):
    #if not self.get_secure_cookie("mycookie"):
    #    self.set_cookie("mycookie", "myvalue")
    #    self.write("Your cookie was not set yet!")
    #else:
    #    self.write("Your cookie was set!")
    def get(self):
        print 'register info:'
        if self.get_arguments("email"):
            email=self.get_argument("email") if self.get_argument("email") else ""
            pwd=self.get_argument("pwd") if self.get_arguments("pwd") else ""
            nickname=self.get_argument("nickname") if self.get_arguments("nickname") else ""
            name=self.get_argument("name") if self.get_arguments("name") else ""
            country=self.get_argument("country") if self.get_arguments("country") else ""
            city=self.get_argument("city") if self.get_arguments("city") else ""
            gender=self.get_argument("gender") if self.get_arguments("gender") else ""
            headpic=self.get_argument("headpic") if self.get_arguments("headpic") else ""

            newUser=[]
            newUser.append(nickname)
            newUser.append(name)
            newUser.append(country)
            newUser.append(city)
            newUser.append(email)
            newUser.append(gender)
            newUser.append(pwd)
            newUser.append(headpic)

            #newUser= ['new','apple','bananaa','bananaa','q@q','bananaa','qqq','bananaa']
            res = insertUserprofile(newUser)
            if res:
                print "register success"
                tmp = readUserprofileOnebyEmailPwd(newUser[4],newUser[6])
                self.set_secure_cookie("userid",str(tmp[0][0]))
                response=[]
                response.append({"userid":str(tmp[0][0]),"state":"1","name":tmp[0][2]})
                self.write(json.dumps(response))
            else:
                print 'register failed!'
                response=[]
                response.append({"state":"0"})
                self.write(json.dumps(response))
                #self.redirect("/login")
        else:
            self.write("please input your account")

class HomePage(BaseHandler):
    def get(self):
        if self.get_secure_cookie("userid"):
            print 'show home page:'
            startPage=self.get_argument("pageNum")
            res = readPictureTop10(int(startPage))
            if res:
                print "get pics success"
                dic= {}
                data=[]
                for i in res:
                    dic={}
                    a = readUserprofileOnebyID(i[1])
                    dic['picid'] = i[0]
                    dic['userid'] = i[1]
                    dic['nickname'] = a[0][1]
                    dic['url']=i[2]
                    dic['uploadtime']=i[3].strftime("%Y-%m-%d %H:%M:%S")
                    dic['place']=i[4]
                    data.append(dic)
                response=[]
                response.append({'state':"1","data":data})
                self.write(json.dumps(response))
            else:
                print 'no picture at all!'

        else:
            response=[]
            response.append({"state":"0"})
            self.write(json.dumps(response))

class friends(BaseHandler):
    def get(self):
        if self.get_secure_cookie("userid"):
            print 'show friends page:'
            print self.get_secure_cookie("userid")
            res = readFriendsAll(self.get_secure_cookie("userid"))
            if res:
                print "get friends success"
                data=[]
                for i in res:
                    friend = readUserprofileOnebyID(i[2])
                    pics = readPicturesbyID(i[2])
                    for pic in pics:
                        dic = {}
                        dic['friend_userid']=i[2]
                        dic['friend_nickname']=friend[0][1]
                        dic['friend_picid'] = pic[0]
                        dic['friend_picurl']=pic[2]
                        dic['friend_uploadtime']=pic[3].strftime("%Y-%m-%d %H:%M:%S")
                        dic['friend_place']=pic[4]
                        data.append(dic)
                response=[]
                response.append({'state':"1","data":data})
                self.write(json.dumps(response))
            else:
                print 'no friend'

        else:
            response=[]
            response.append({"state":"0"})
            self.write(json.dumps(response))


