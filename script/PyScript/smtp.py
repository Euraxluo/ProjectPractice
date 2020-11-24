import smtplib  # smtp服务器
from email.mime.text import MIMEText  # 邮件文本 
# 邮件构建
subject = "滴滴答答"  # 邮件标题
# sender = "euraxluo@yeah.net"  # 发送方
sender = "luoyou@dml-express.com"  # 发送方
# password = "HOCMKUTLFWKXJPNV"
password = "Z7TfzNgmd7XGQs9"
content = "Test！"
recver = "euraxluo@qq.com"  # 接收方
message = MIMEText(content, "plain", "utf-8")# content 发送内容     "plain"文本格式   utf-8 编码格式
message['Subject'] = subject  # 邮件标题
message['To'] = recver  # 收件人
message['From'] = sender  # 发件人
try:    
    # smtp = smtplib.SMTP_SSL("smtp.yeah.net", 994)  # 实例化smtp服务器    
    smtp = smtplib.SMTP_SSL("smtp.exmail.qq.com", 465)  # 实例化smtp服务器    
    smtp.login(sender, password)  # 发件人登录    
    smtp.sendmail(sender, [recver], message.as_string())  # as_string 对 message 的消息进行了封装    
    print("邮件发送成功")
except smtplib.SMTPException:    
    print("Error: 无法发送邮件")