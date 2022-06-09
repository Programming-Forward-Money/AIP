import json
import re
import requests
import pandas as pd
import time
import random
import os


def get_html_json_data(url):
    html = requests.get(url)
    text = html.text
    pat = '"data":\{(.*?)\}'
    data = re.compile(pat, re.S).findall(text)[0]
    json_data = json.loads('{' + data + '}')
    return json_data


def save_data(json_data):
    klines_data = json_data['klines']
    keys = ['date', 'open', 'close', 'high', 'low', 'volume', 'amount', 'amplitude', 'changeAmplitude', 'changeAmount',
            'exchangeRatio']
    with open('./data/' + json_data['name'] + '.txt', 'w') as fw:
        for value in klines_data:
            values = value.split(',')
            dic = dict(zip(keys, values))
            fw.write(str(dic) + '\n')

    # df = pd.DataFrame(klines_data)
    # df.to_excel(json_data['name'] + '.xlsx', index=False)
    # df.to_csv(json_data['name'] + '.csv', index=False)


def get_url_dict(file_name):
    with open(file_name, 'r') as fr:
        dic = {}
        lines = fr.readlines()
        for line in lines:
            name, url = line.split(' ')
            dic[name] = url
    return dic


def start():
    data_dir = './data'
    file = 'stock_url.txt'
    url_dict = get_url_dict(file)
    data_file_list = []
    for file in os.listdir(data_dir):
        data_file_list.append(file.split('.')[0])
    for stock_name, stock_url in url_dict.items():
        if data_file_list.__contains__(stock_name):
            continue
        seconds = random.randint(3, 5)
        print("random sleep:", seconds)
        time.sleep(seconds)
        print('=== start get ' + stock_name + ' ====')
        stock_json_data = get_html_json_data(stock_url)
        save_data(stock_json_data)
        print('************** save data ' + stock_name + ' *****************')


#  pip install -i https://pypi.tuna.tsinghua.edu.cn/simple pandas
# pip install pip -U
# pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple
if __name__ == '__main__':
    start()
