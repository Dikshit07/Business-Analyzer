# app.py
from flask import Flask,render_template
import pandas as pd
from ydata_profiling import ProfileReport


app = Flask(__name__)

@app.route('/runPython', methods=['GET'])
def run_python():





    
    return render_template('Pythonreport.html')

if __name__ == '__main__':
    app.run(port=5000)