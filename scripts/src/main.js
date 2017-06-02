import Weather from "./weather";
import Map from "./map";

$(function() {
  let weather = new Weather();

  let map = new Map();
  //検索ボタン
  $('#search-city').click(() => {
    let newCity = $('#input-city').val();
    weather.send(newCity, map);

  });
  $("#city-name").text(weather.city);
});
