#from numpy import append

__author__ = 'zxz'

import sys
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import os.path
import re

from tornado.options import define,options
from RequestHandler import *

define("port",default=8888,help="run on this port",type=int)
define("mysql_host",default="172.19.208.96:3306",help="tabiagent database")
define("mysql_database",default="tabiagentdb",help="database name")
define("mysql_user",default="root",help="database owner")
define("mysql_passwd",default="123456",help="root passwd")


class Application(tornado.web.Application):
    def __init__(self):
        settings=dict(
            cookie_secret="43oETzKXQAGaYdkL5gEmGeJJFuYh7EQnp2XdTP1o/Vo=",
            login_url="/login",
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
        )
        path = os.path.join(os.path.dirname(__file__), "static")
        handlers=[
            (r"/login",login),
            (r"/logout",logout),
            (r"/register",register),
            (r"/home",HomePage),
            (r"/friends",friends),
            (r"/search",search),
            #(r"/referInf",storeInf),
            #(r"/",abc),
            (r"/static/(.*)",tornado.web.StaticFileHandler,dict(path=path))
            ]
        tornado.web.Application.__init__(self,handlers,**settings)

def main():
    tornado.options.parse_command_line()
    http_server=tornado.httpserver.HTTPServer(Application())
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()

if __name__ == "__main__":
    main()
