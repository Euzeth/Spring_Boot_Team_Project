<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/412379eca8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.css">
    <title>음악 플레이어</title>
  <style>
	<%@include file="/resources/static/css/song_Page.css" %>
  </style>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<body>

<div class="container">
    <h1>음악 플레이어</h1>
    <div class="music-container" id="musicContainer">
        <div class="music-info">
            <h4 id="title">노래 제목</h4>
            <div class="progress-container" id="progress-container">
                <div class="progress" id="progress"></div>
            </div>
        </div>
        <audio id="audio" src="" onloadstart="this.volume=0.005"></audio>
        <div class="img-container">
            <img src="" alt="cover" id="cover">
        </div>
        <div class="navigation">
            <button id="prev" class="action-btn"><i class="fa-sharp fa-solid fa-backward"></i></button>
            <button id="play" class="actiong-btn big"><i class="fa-solid fa-play"></i></button>
            <button id="next" class="actiong-btn"><i class="fa-sharp fa-solid fa-forward"></i></button>
        </div>


    </div>


</div>


<script>
	const playBtn = document.getElementById("play");
	const musicContainer = document.getElementById("musicContainer");
	const audio = document.getElementById("audio");
	const prevBtn = document.getElementById("prev");
	const nextBtn = document.getElementById("next");
	const progress = document.getElementById("progress");
	const progressContainer = document.getElementById('progress-container');
	const imgCover = document.getElementById("cover");
	const title = document.getElementById("title");
	
	const songs = ["hey", "summer", "ukulele"];
	
	let songIndex = 2;
	
	loadSong(songs[songIndex]);
	
	function loadSong(song) {
	  title.innerText = song;
	  audio.src = `http://127.0.0.1:5500/music/${song}.mp3`;
	  imgCover.src = `http://127.0.0.1:5500/images/${song}.jpg`;
	}
	
	function playMusic() {
	  musicContainer.classList.add("play");
	
	  playBtn.innerHTML = `<i class="fa-solid fa-pause"></i>`;
	
	  audio.play();
	}
	
	function pauseMusic(){
	    musicContainer.classList.remove('play');
	    playBtn.innerHTML = `<i class="fa-solid fa-play"></i>`;
	
	    audio.pause();
	}
	
	function playPrevSong() {
	    songIndex--;
	
	    if (songIndex < 0) {
	      songIndex = songs.length - 1;
	    }
	
	    loadSong(songs[songIndex]);
	
	    playMusic();
	}
	
	function playNextSong (){
	    songIndex++;
	
	    if(songIndex > 2){
	        songIndex = 0;
	    }
	
	    loadSong(songs[songIndex]);
	    playMusic();
	}
	
	function updateProgress(e){
	    const {duration, currentTime} = e.srcElement;
	
	    const progressPer = (currentTime / duration) * 100;
	
	    progress.style.width = `${progressPer}%`;
	}
	
	function changeProgress(e){
	
	    const width = e.target.clientWidth; // 전체 넓이
	
	    const offsetX = e.offsetX; // 현재 x 좌표;
	
	    const duration = audio.duration; // 재생길이
	
	    audio.currentTime = (offsetX / width) * duration;
	
	}
	
	
	playBtn.addEventListener("click", () => {
	    const isPlaying = musicContainer.classList.contains('play');
	    const musicFileName = "5 Seconds Of Summer-02-Want You Back-320k.mp3";
	    const isLoop = true;
	
	
	    if (isPlaying) {
	
	        fetch('/pause?name=${musicFileName}', {
	            method: 'GET',
	        })
	        .then(response => {
	            if (response.ok) {
	                pauseMusic();
	            } else {
	                console.error('서버 오류: ' + response.status);
	            }
	        })
	        .catch(error => {
	            console.error('오류 발생: ' + error);
	        });
	    } else {
	        fetch('/play?name=${musicFileName}&isLoop=${isLoop}', {
	            method: 'GET',
	        })
	        .then(response => {
	            if (response.ok) {
	                playMusic();
	            } else {
	                console.error('서버 오류: ' + response.status);
	            }
	        })
	        .catch(error => {
	            console.error('오류 발생: ' + error);
	        });
	    }
	});
	
	prevBtn.addEventListener("click", playPrevSong);
	nextBtn.addEventListener('click', playNextSong);
	audio.addEventListener('ended', playNextSong);
	audio.addEventListener('timeupdate', updateProgress);
	
	progressContainer.addEventListener('click', changeProgress);
</script>

</form>
</body>
</html>