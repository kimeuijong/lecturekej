# pip install flask==1.1.4
from flask import Flask

app = Flask(__name__, static_folder='.', static_url_path='')

@app.route("/alert/")
def echo():
    return "OK readiness & liveness"


app.run(host='0.0.0.0', port=8084, debug=True)
