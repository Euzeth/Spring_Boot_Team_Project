    const volumeSlider = document.getElementById("volume-slider");
    const musicWrap = document.querySelector(".wrap__music");
    const musicImg = musicWrap.querySelector(".music__img img");
    const musicName = musicWrap.querySelector(".music__song .name");
    const musicArtist = musicWrap.querySelector(".music__song .artist");
    const musicAudio = musicWrap.querySelector("#main-audio");
    const musicPlay = musicWrap.querySelector("#control-play");
    const musicPrevBtn = musicWrap.querySelector("#control-prev");
    const musicNextBtn = musicWrap.querySelector("#control-next");
    const musicProgress = musicWrap.querySelector(".music__progress");
    const musicProgressBar = musicProgress.querySelector(".bar");
    const musicProgressCurrent = musicProgress.querySelector(".current");
    const musicProgressDuration = musicProgress.querySelector(".duration");
    const musicRepeat = musicWrap.querySelector("#control-repeat");
    const musicList = musicWrap.querySelector(".music__list");
    const MusicListBtn = musicWrap.querySelector("#control-list");
    const MusicListClose = musicList.querySelector(".close");
    const musicListUl = musicList.querySelector(".list ul");
    const MusicClick = document.querySelectorAll(".Music-Click");
    const musicOff = document.querySelector(".material-icons icon-left");




  let allMusic = JSON.parse(localStorage.getItem("allMusic")) || [];
      // allMusic 배열을 콘솔에 출력
      console.log("allMusic : "+allMusic);
      var numIndex = localStorage.getItem("addMusicIndex");
      console.log("numIndex : "+numIndex);
      let musicIndex =1;


    function addMusic(num) {
        addMusicIndex = allMusic[num-1].dataIndex;
        localStorage.setItem("addMusicIndex",num);
        console.log("allMusic : " + allMusic);
        localStorage.setItem("allMusic", JSON.stringify(allMusic));
        console.log("LocalStorage.setItem : " + localStorage);
    }


    // 볼륨

    volumeSlider.addEventListener("input", () => {
        const volume = volumeSlider.value / 100;
        musicAudio.volume = volume;
    });



    // 음악 재생
    function loadMusic(num){
        musicName.innerText = allMusic[num-1].title;
        musicArtist.innerText = allMusic[num-1].artist;

        title = allMusic[num-1].title;
        console.log("AudioPlayTitle : " + title);
      //   var encodedTitle  = encodeURIComponent(title);
        musicAudio.src = "http://localhost:8080/audio/"+encodedTitle +".mp3";
        console.log("musicAudio.src : " + musicAudio.src);

        addMusic(musicIndex);
        console.log("musicIndex : " +musicIndex);

    }

    // 음악정보가져오기
    MusicClick.forEach(element => {
        element.addEventListener("click", ()=>{
                var buttonText = element.textContent;
                console.log("ButtonText : " + buttonText);
                 axios.post('/musicinfo', {
                         title : buttonText
                     })
                     .then(function (response) {

                         var title = response.data.title;
                         var artist = response.data.artist;
                         var musicPath = response.data.music_path;
                         var index = allMusic.length+1;

                         allMusic.push({
                             artist : artist,
                             title : title,
                             musicPath : musicPath,
                             dataIndex : index
                         });
                         console.log(allMusic);

                        console.log(allMusic);
                        addList();
                        console.log("index : " + index);
                        loadMusic(index);
                        playMusic();
                        playListMusic();



                    })
                    .catch(function (error) {
                        console.error("Mylist add fail.. : ", error);
                    });


        })


    // 리스트 추가
    function addList(){
        let i = allMusic.length -1;
        let li = `
               <li data-index="${allMusic[i].dataIndex}">
                    <div>
                       <em>${allMusic[i].title}</em>
                       <p>${allMusic[i].artist}</p>
                    </div>
                        <audio class="${allMusic[i].audio}" "></audio>
                        <span id="${allMusic[i].audio}" class="audio-duration">3:36</span>
               </li>
               `;
              musicListUl.insertAdjacentHTML("beforeend", li);
              console.log("li" + li);

              let liAudioDuration = musicListUl.querySelector(`#${allMusic[i].audio}`);
              let liAudio = musicListUl.querySelector(`.${allMusic[i].audio}`);

              liAudio.addEventListener("loadeddata", () => {
                  let audioDuration = liAudio.duration;
                  let totalMin = Math.floor(audioDuration / 60);
                  let totalSec = Math.floor(audioDuration % 60);
                  if (totalSec < 10) totalSec = `0${totalSec}`;

                  liAudioDuration.innerText = `${totalMin}:${totalSec}`;
                  liAudioDuration.setAttribute("data-duration", `${totalMin}:${totalSec}`);
        });
    }


    // 플레이 버튼
    function playMusic(){
        musicWrap.classList.add("paused");
        musicPlay.innerText = "pause";
        musicPlay.setAttribute("title", "일시정지")
        musicAudio.play();

    }

    // 일시정지 버튼
    function pauseMusic(){
        musicWrap.classList.remove("paused");
        musicPlay.innerText = "play_arrow";
        musicPlay.setAttribute("title", "재생")
        musicAudio.pause();


    }

    // 이전 곡 듣기 버튼
    function prevMusic(){
        musicIndex--;
        musicIndex < 1 ? musicIndex = allMusic.length : musicIndex = musicIndex;
        loadMusic(musicIndex);
        playMusic(musicIndex);
        playListMusic(musicIndex);
    }

    // 다음 곡 듣기 버튼
    function nextMusic(){
        musicIndex++;
        musicIndex > allMusic.length ? musicIndex = 1 : musicIndex = musicIndex;
        loadMusic(musicIndex);
        playMusic(musicIndex);
        playListMusic(musicIndex);
    }

    // 뮤직 진행바
    musicAudio.addEventListener("timeupdate", (e)=>{
        const currentTime = e.target.currentTime;
        const duration = e.target.duration;
        let progressWidth = (currentTime/duration) * 100;
        musicProgressBar.style.width = `${progressWidth}%`

        musicAudio.addEventListener("loadeddata", ()=>{
            let audioDuration = musicAudio.duration;
            let totalMin = Math.floor(audioDuration / 60);
            let totalSec = Math.floor(audioDuration % 60);
            if (totalSec < 10) totalSec = `0${totalSec}`;

            musicProgressDuration.innerText = `${totalMin}:${totalSec}`;
        })

        let currentMin = Math.floor(currentTime / 60);
        let currentSec = Math.floor(currentTime % 60);
        if (currentSec < 10) currentSec = `0${currentSec}`;
        musicProgressCurrent.innerText = `${currentMin}:${currentSec}`
    })

    // 진행 버튼
    musicProgress.addEventListener("click", e=>{
        let progressWidth = musicProgress.clientWidth;
        let clickedOffsetX = e.offsetX;
        let songDuration = musicAudio.duration;

        musicAudio.currentTime = (clickedOffsetX / progressWidth) * songDuration;
        playMusic();
    })




    // 재생/일시정지
    musicPlay.addEventListener("click", ()=>{
        const isMusicPaused = musicWrap.classList.contains("paused");
        isMusicPaused ? pauseMusic() : playMusic();
    })

    musicPrevBtn.addEventListener("click", ()=>{
        prevMusic();
    });
    musicNextBtn.addEventListener("click", ()=>{
        nextMusic();
    });

    // 반복 버튼
    musicRepeat.addEventListener("click", ()=>{
        let getText = musicRepeat.innerText;

        switch(getText){
            case "repeat" :
                musicRepeat.innerText = "repeat_one";
                musicRepeat.setAttribute("title", "한곡 반복")
            break;

            case "repeat_one" :
                musicRepeat.innerText = "shuffle";
                musicRepeat.setAttribute("title", "랜덤 반복")
            break;

            case "shuffle" :
                musicRepeat.innerText = "repeat";
                musicRepeat.setAttribute("title", "전체 반복")
                playListMusic();
            break;
        }
    })

    // 오디오가 끝나고
    musicAudio.addEventListener("ended", ()=>{
        let getText = musicRepeat.innerText;

        switch(getText){
            case "repeat" :
                nextMusic();
            break;

            case "repeat_one" :
                loadMusic(musicIndex);
                playMusic();
            break;

            case "shuffle" :
                let randIndex = Math.floor((Math.random() * allMusic.length) + 1);
                do {
                    randIndex = Math.floor((Math.random() * allMusic.length) + 1);
                } while (musicIndex == randIndex);
                musicIndex = randIndex;
                loadMusic(musicIndex);
                playMusic();
                playListMusic();
            break;
        }
    })

    // 뮤직 리스트 버튼
    MusicListBtn.addEventListener("click", ()=>{
        musicList.classList.add("show");
    })

    // 뮤직 리스트 닫기 버튼
    MusicListClose.addEventListener("click", ()=>{
        musicList.classList.remove("show");
    })

    // 뮤직 리스트 구현하기


    // 뮤직 리스트 클릭하기

    function playListMusic(){
        const musicListAll = musicListUl.querySelectorAll("li");
        console.log("musicListAll : " +musicListAll.length);

        for(let j=0; j<musicListAll.length; j++){
        let audioTag = musicListAll[j].querySelector(".audio-duration");
        let adDuration = audioTag.getAttribute("data-duration");

        if (musicListAll[j].classList.contains("playing")) {
            musicListAll[j].classList.remove("playing");
            audioTag.innerText = adDuration;
        }

        if (musicListAll[j].getAttribute("data-index") == musicIndex) {
            musicListAll[j].classList.add("playing");
            audioTag.innerText = "재생중";
            console.log("musicIndex : "+musicIndex);
        }
        musicListAll[j].setAttribute("onclick", "clicked(this)");
    }
  }

    function clicked(el){

        let getLiIndex = el.getAttribute("data-index");
        musicIndex = getLiIndex;
        console.log("musicIndex 변화 : " + musicIndex);

        loadMusic(musicIndex);
        playMusic();
        playListMusic();
    }
      // 창이 열리면 노래 시작
       window.addEventListener("load", ()=>{
              addList(numIndex);
              loadMusic(numIndex);
              playMusic();
              playListMusic(numIndex);
          });