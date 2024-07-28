import webbrowser
import os
from flask import Flask, render_template
import pandas as pd
from ydata_profiling import ProfileReport

# Specify the correct path to your templates directory
# template_dir = '/Users/dikkuu/Desktop/pr2/projecttwo/src/main/java/com/project2/projecttwo/resources/templates'

app = Flask(__name__)

@app.route('/runPython', methods=['GET'])
def run_python():
    # Load the dataset
    df = pd.read_csv('/Users/dikkuu/Desktop/Business/report.csv')
    
    # Generate the profile report
    profile = ProfileReport(df)
    profile.to_file(output_file='/Users/dikkuu/Desktop/Business/report.html')
    
    # Define the file path to your HTML file
    file_path = '/Users/dikkuu/Desktop/Business/report.html'
    abs_file_path = os.path.abspath(file_path)
    
    # Open the HTML file in the default web browser
    webbrowser.open(f'file://{abs_file_path}')
    
    return render_template('report.html')

if __name__ == '__main__':
    app.run(port=5000)