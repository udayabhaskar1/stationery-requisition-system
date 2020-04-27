import json
from flask import Flask, request, make_response, jsonify
import csv
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix
from sklearn.ensemble import RandomForestRegressor  
from sklearn.linear_model import LinearRegression


app = Flask(__name__) 

month_dic ={1:"jan", 2:"feb",3:"mar",4:"apr",5:"may",6:"jun",7:"jul",8:"aug",9:"sep",10:"oct",11:"nov",12:"dec"}
data = pd.read_csv("logic.csv")
d ={}

for index,item in enumerate(list(data["Item Category"].unique())):
    d[item]=index+1

data["Item Category"] = data["Item Category"].map(d)

datanew = data
data_new =datanew.groupby(['Item Category','month'],as_index=False)
data_ = data_new["Requested"].agg("sum")

def predict_res(item_no,month):
    X = data_[['Item Category','month']]
    y = data_["Requested"]
    X_train, X_test, y_train, y_test = train_test_split( X, y, random_state=1)
    model = RandomForestRegressor(n_estimators = 10,random_state=1)
    model.fit(X_train, y_train)
    res = model.predict([[int(item_no),int(month)]])
    return res

def getmonthvalues(item_,month_):
    month1 = month_-1
    month2 = month_-2
    
    if month1 == 0:
        month1=12
    if month2==0:
        month2=12
    if month2 == -1:
        month2=11

    previous_months = [month1,month2]
    
    month_values=[]    # pylint: disable=unbalanced-tuple-unpacking
    for item in previous_months:
        month_values.append([list(data_[(data_["Item Category"]== item_) & (data_["month"]== item)]["Requested"])[0],month_dic[item]])
        
    return month_values

@app.route('/', methods=['GET'])
def predict():
    month_dic ={1:"jan", 2:"feb",3:"mar",4:"apr",5:"may",6:"jun",7:"jul",8:"aug",9:"sep",10:"oct",11:"nov",12:"dec"}
    item_no = int(request.args.get('id',3))
    month = int(request.args.get('mon',3))

    res={}
    res[month_dic[month]]=int(predict_res(item_no,month)[0])
    x, y= getmonthvalues(item_no,month) # pylint: disable=unbalanced-tuple-unpacking
    res[x[1]] =x[0]
    res[y[1]] =y[0]
	
    return jsonify(status="ok", result = res)
 
app.run(debug=True, port = 3000)