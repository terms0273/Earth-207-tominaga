var webpack = require('webpack');
var saveLicense = require('uglify-save-license');

module.exports = { 
  entry: { 'main': './scripts/src/main.js' }, 
  output: { path: __dirname + '/public/javascripts/', filename: '[name].js' }, 
  resolve: { extensions: ['.js', '.jsx'] },
  plugins: [ 
    new webpack.DefinePlugin({ 
      "process.env": { NODE_ENV: JSON.stringify('production') }
    }),
    // リリース時はコメントを残してMinify化
    new webpack.optimize.UglifyJsPlugin({ 
      output: { comments: saveLicense }, 
      compress: { warnings: false }, 
      sourceMap: false 
    }), 
  ], 
  module: { 
    loaders: [{ 
      test: /\.jsx?$/, 
      loader: 'babel-loader', 
      exclude: /node_modules/ 
    }] 
  },
};
