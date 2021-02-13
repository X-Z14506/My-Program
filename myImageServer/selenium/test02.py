from selenium import webdriver
import time

driver = webdriver.Firefox()
driver.get("http://localhost:8080/index.html")
driver.maximize_window()

# 上传图片测试
driver.find_element_by_name("upload").send_keys("C:\\Users\\123\\Desktop\\护眼模式\\image2.jpg")
time.sleep(5)
driver.find_element_by_xpath("/html/body/nav/div/form/div[2]/input").click()
time.sleep(5)

# 删除图片测试
driver.find_element_by_xpath("/html/body/div[1]/figure/div/div[3]/button").click()
time.sleep(3)
alert = driver.switch_to.alert
alert.accept()
driver.find_element_by_xpath("/html/body/div[1]/figure/div/div[7]/button").click()
alert = driver.switch_to.alert
alert.accept()
time.sleep(5)
driver.quit()