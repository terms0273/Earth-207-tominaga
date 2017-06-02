var webpack = require('webpack');

module.exports = {
    entry: { 'main': './scripts/src/main.js' },
    output: { path: __dirname + '/public/javascripts/', filename: '[name].js' },

    devtool: 'source-map',
    module: {
        loaders: [{
            test: /\.jsx?$/,
            loader: 'babel-loader',
            exclude: /node_modules/,
        }]
    },
    resolve: { extensions: ['.js', '.jsx'] }
};