<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <script src="../build/react.js"></script>
    <script src="../build/react-dom.js"></script>
    <script src="../build/browser.min.js"></script>
    <script src="../build/jquery.min.js"></script>
    <title>QA DB</title>
</head>
<body>

<div id="formDIV"></div>

<script type="text/babel">

    var FormDOM = React.createClass({
        getInitialState: function () {
            return {value: 'Init',backColor: 'red'};
        },
        request: function () {
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/selectQA/1",
                success: function (data) {
                    console.log(data);
                }.bind(this)
            });
        },
        handleChange: function (e) {
            this.setState({value: e.target.value});
        },
        handleClick: function (e) {
            this.setState({backColor: this.state.backColor === 'red'?'green':'red'});
            this.request();
        },
        render: function () {
            var value = this.state.value;
            var color = this.state.backColor;
            return (
            <div>
                    <input type="text" value={value} onChange={this.handleChange} ref="idRef"/>
                    <input type="text" value={value} onChange={this.handleChange} ref="questionRef"/>
                    <input type="text" value={value} onChange={this.handleChange} ref="answerRef">
                    <button onClick={this.handleClick}>Search </button>
                    <button onClick={this.handleClick}>Update </button>
            </div>
            );
        }
    });

    ReactDOM.render(
    <FormDOM/>, document.getElementById('formDIV')
    );

</script>
</body>
</html>
