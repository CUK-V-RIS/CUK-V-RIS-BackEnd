import requests
import json
import pymysql
from pprint import pprint

baseURL = "http://openapi.seoul.go.kr:8088"
apikey = "796250726239386839375770486d4d"
targetAPI = "CrtfcUpsoInfo"


URL = f"{baseURL}/{apikey}/json/{targetAPI}/1/1"
r = requests.get(URL)
data = json.loads(r.text)
data = data['CrtfcUpsoInfo']
total_count = data['list_total_count']
print(f" >> there are {total_count} rows in OpenAPI")



conn = pymysql.connect(
    host='database-1.cryrdazpyi5g.ap-northeast-2.rds.amazonaws.com',
    user='khj',
    password='rlagkwls',
    db='v_ris_db',
    charset='utf8'
    )
curs = conn.cursor()


sql = """delete from restaurant where res_idx >=1"""
curs.execute(sql)
sql = """ALTER TABLE restaurant AUTO_INCREMENT = 1"""
curs.execute(sql)
conn.commit()


for startIdx in range(1, total_count+1, 500):
    endIdx = startIdx + 500 - 1
    print(f"[+] loading data from OpenAPI {startIdx}...{endIdx}")
    URL = f"{baseURL}/{apikey}/json/{targetAPI}/{startIdx}/{endIdx}"
    r = requests.get(URL)
    data = json.loads(r.text)
    data = data['CrtfcUpsoInfo']
    data = data['row']

    useful_data = []
    for item in data:
        useful_item = (item['RDN_CODE_NM'], item['BIZCND_CODE_NM'], item['FOOD_MENU'], item['UPSO_NM'], item['TEL_NO'], item['X_CNTS'], item['Y_DNTS'])
        useful_data.append(useful_item)


    sql = """insert into restaurant(res_address, res_category, res_menu, res_name, res_num, res_loc_x, res_loc_y) values (%s, %s, %s, %s, %s, %s, %s)"""
    curs.executemany(sql, useful_data)
    conn.commit()


conn.close()