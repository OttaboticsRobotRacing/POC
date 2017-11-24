from flask import Flask, render_template
app = Flask(__name__)

@app.route('/')
def index():
    return render_template("index.html")

@app.route('/test')
def test_route():
    return "test route!?!"

# app.add_url_rule("/", "/test", hello_world)

@app.route('/hello/<name>')
def hello_(name):
    return render_template('hello.html', name=name)

if __name__ == '__main__':
    app.debug = True
    # app.run()
    app.run(debug=True, host="localhost")
