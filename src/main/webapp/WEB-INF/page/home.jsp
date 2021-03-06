<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <script src="../build/react.js"></script>
    <script src="../build/react-dom.js"></script>
    <script src="../build/browser.min.js"></script>
    <script src="../build/jquery.min.js"></script>
    <title>Based-QA-Robot</title>
</head>
<body>
<h2>Hello xxx0624!</h2>

<!--todo-->
<div id="formDIV"></div>

<div id="buildIndex"></div>

<!--todo-->
<div id="deleteAllIndex"></div>

<script type="text/babel">

    var FormDOM = React.createClass({
        getInitialState: function () {
            return {value: 'Init',backColor: 'red'};
        },
        request: function () {
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/segmentword/1",
                success: function (data) {
                    this.refs.showSegmentWordRef.innerText = data.message + "( " + data.data + " )";
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
                    <input type="text" value={value} onChange={this.handleChange} ref="inputDOMRef"/>
                    <button onClick={this.handleClick} style={{backgroundColor: color}}>
                        Search
                    </button>
                        <label ref="showSegmentWordRef"></label>
                    </div>
            );
        }
    });

    ReactDOM.render(
        <FormDOM/>, document.getElementById('formDIV')
    );


    var BuildIndexBtnDom = React.createClass({
        request: function () {
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/index/rebuildIndex",
                success: function (data) {
                    //console.log(data);
                    this.refs.showBuildIndexRef.innerText = data.message + "( " + data.data + " )";
                    //console.log(this.refs.btnDomResultRef);
                }.bind(this)
            });
        },
        handleClick: function (e) {
            console.log("send a request");
            this.request();
        },
        render: function () {
            return (
                    <div>
                    <button onClick={this.handleClick}>
                        Build Index
                    </button>
                    <label ref="showBuildIndexRef"></label>
                    </div>
            );
        }
    });

    ReactDOM.render(
         <BuildIndexBtnDom/>, document.getElementById('buildIndex')
    );

</script>
</body>
</html>
