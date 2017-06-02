export default class Weather{
  constructor(){
    this.city = "tokyo";
  }
  print(json){
    this.city = json.name;
    $('#icon').html("<img src='http://openweathermap.org/img/w/" + json.weather[0].icon + ".png'>")
    $('#city-name').text(this.city);
    $('#weather').html(json.weather[0].description);
    let temp = new Number(json.main.temp - 273);
    temp = temp.toFixed(1);
    $('#temperature').text((temp) + "℃");
    $('#wind').html((json.wind.speed) + "m/s");
    $('#cloud').html((json.clouds.all) + "%");
    $('#pressure').html((json.main.pressure) + "hPa");
    $('#humidity').html((json.main.humidity) + "%");
    let sunrise = new Date(json.sys.sunrise * 1000);
    $('#sunrise').text(sunrise);
    let sunset = new Date(json.sys.sunset * 1000);
    $('#sunset').text(sunset);
    $('#latlon').html((json.coord.lon) + ":" + (json.coord.lat));
    this.lat = json.coord.lat;
    this.lon = json.coord.lon;
    }

  send(cityName, map){
    let url = 'http://api.openweathermap.org/data/2.5/weather?q=' +
      cityName +
      '&APPID=cc6cc467cb8a060a9dc7621152da02e0';

    $.ajax({
      url: url
    }).then((json) => {
      this.print(json);
      console.log(json);
      map.changeLocate(this.lat, this.lon);
    }, (err) => {
      console.log(err);
    });
  }
}
