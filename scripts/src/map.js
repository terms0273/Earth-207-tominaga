import L from 'leaflet';

export default class Map{
  constructor(){
    this.map = {};
    this.init();
  }
  init(){
    // map要素が無い場合は地図画面ではない
   if (!$("#map")) {
     console.log("this page is not map page");
     return;
   }

   let std = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
     attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
   });

   let chiriin = L.tileLayer('https://cyberjapandata.gsi.go.jp/xyz/std/{z}/{x}/{y}.png', {
     attribution: "<a href='http://portal.cyberjapan.jp/help/termsofuse.html' target='_blank'>国土地理院</a>"
   });

   let pf = L.tileLayer('http://tile.openweathermap.org/map/precipitation_new/{z}/{x}/{y}.png?appid=cc6cc467cb8a060a9dc7621152da02e0', {
     attribution: '<a href="">precipitation</a>',
     // opacity: 0.8
   });

   let pr = L.tileLayer('http://tile.openweathermap.org/map/pressure_new/{z}/{x}/{y}.png?appid=cc6cc467cb8a060a9dc7621152da02e0', {
     attribution: '<a href="">pressure</a>',
     // opacity: 0.8
   });


   let wi = L.tileLayer('http://tile.openweathermap.org/map/wind_new/{z}/{x}/{y}.png?appid=cc6cc467cb8a060a9dc7621152da02e0', {
     attribution: '<a href="">wind</a>',
     // opacity: 0.8
   });

   let te = L.tileLayer('http://tile.openweathermap.org/map/temp_new/{z}/{x}/{y}.png?appid=cc6cc467cb8a060a9dc7621152da02e0', {
     attribution: '<a href="">tempressure</a>',
     // opacity: 0.8
   });

   this.map = L.map("map", {
     center: [37.09, 138.52],
     zoom: 16,
     layers: [std]
   });

   //主題図レイヤーグループ化
   let baseMaps ={
     "Mapbox(osm)": std,
     "Mapbox(chiriin)": chiriin
   };

   //オーバーレイヤー
   let overLayerMaps = {
         'Precipitation': pf,
         'Pressure': pr,
         'Wind': wi,
         'Temperature': te
   };

   L.control.layers(baseMaps, overLayerMaps).addTo(this.map);


   //スケールバー追加
   L.control.scale().addTo(this.map);



   this.map.on('resize', () => {
     this.map.invalidateSize();
   });
  }
  changeLocate(lat, lon){
    this.map.setView(new L.LatLng(lat, lon), 16);
  };
}
