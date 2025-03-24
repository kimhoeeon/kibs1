$(document).ready(function () {
    // 메인 상단
    var swiper = new Swiper('.swiperMainTop', {
        slidesPerView: 1,
        spaceBetween: 0,
        effect:'fade',
        speed:2000,
        direction: getDirection(),
        touchRatio: 0,
        autoplay: {
            delay: 3000,
            disableOnInteraction: false,
        },
        loop: true,
        on: {
            resize: function () {
                swiper.changeDirection(getDirection());
            },
        },
    });


    // 메인 KIBS TV
    var swiper = new Swiper('.swiper_kibstv', {
        slidesPerView: 1,
        spaceBetween: 20,
        direction: getDirection(),
        autoplay: {
            delay: 2500,
            disableOnInteraction: false,
        },
        loop: true,
        breakpoints: {
            575: { // 575px ~ 991px
                slidesPerView: 2,
            },

            992: { // 992px ~ 1299px
                slidesPerView: 2,
                spaceBetween: 20,
            },

            1300: {  // 1300px ~ 
                slidesPerView: 3,
                spaceBetween: 20,
            },
        },

        navigation: {
            nextEl: '.swiper_kibstv_next',
            prevEl: '.swiper_kibstv_prev',
        },
        on: {
            resize: function () {
                swiper.changeDirection(getDirection());
            },
        },
    });

    // 메인 참가기업
    var swiper = new Swiper('.swiper_exh', {
        slidesPerView: 1,
        spaceBetween: 20,
        direction: getDirection(),
        // autoplay: {
        //     delay: 2500,
        //     disableOnInteraction: false,
        // },
        loop: true,
        breakpoints: {
            575: { // 575px ~ 991px
                slidesPerView: 2,
            },

            992: { // 992px ~ 1299px
                slidesPerView: 2,
                spaceBetween: 20,
            },

            1300: {  // 1300px ~ 
                slidesPerView: 3,
                spaceBetween: 20,
            },
        },

        navigation: {
            nextEl: '.swiper_exh_next',
            prevEl: '.swiper_exh_prev',
        },
        on: {
            resize: function () {
                swiper.changeDirection(getDirection());
            },
        },
    });

    // 갤러리 게시판
    var swiper = new Swiper('.swiper_gallery', {
        slidesPerView: 1,
        spaceBetween: 20,
        direction: getDirection(),
        loop: true,
        navigation: {
            nextEl: '.swiper_gallery_next',
            prevEl: '.swiper_gallery_prev',
        },
        on: {
            resize: function () {
                swiper.changeDirection(getDirection());
            },
        },
    });

    // 제품
    var prdThumbs = new Swiper('.prdThumb .swiper-container', {
        spaceBetween: 10,
        slidesPerView: 5,
        freeMode: true,
        watchSlidesVisibility: true,
        watchSlidesProgress: true,
        breakpoints: {
            992: { // ~ 992px 
                slidesPerView: 10,
            },
        },
    });

    var prdTop = new Swiper('.prdImg .slider', {
        spaceBetween: 10,
        navigation: {
            nextEl: '.prdImg .swiper_prdImg_next',
            prevEl: '.prdImg .swiper_prdImg_prev',
        },
        thumbs: {
            swiper: prdThumbs
        }
    });

    // 지난전시회 주요사진
    var thumbSwiper = new Swiper('.swiper_histPhoto', {
        slidesPerView: 1,
        spaceBetween: 20,
        direction: getDirection(),
        loop: true,
        breakpoints: {
            575: { // 575px ~ 991px
                slidesPerView: 2,
            },

            992: { // 992px ~ 1299px
                slidesPerView: 3,
                spaceBetween: 20,
            },

            1300: {  // 1300px ~ 
                slidesPerView: 4,
                spaceBetween: 20,
            },
        },
        navigation: {
            nextEl: '.swiper_histPhoto_next',
            prevEl: '.swiper_histPhoto_prev'
        },
        on: {
            resize: function () {
                swiper.changeDirection(getDirection());
            },

        },
    });


    //갤러리 게시판
    var swiper = new Swiper('.swiper_award_hist', {
        // direction: "vertical",
        slidesPerView: 1,
        spaceBetween: 20,
        autoHeight : true,
        loop: true,
        navigation: {
            nextEl: '.swiper_award_hist_next',
            prevEl: '.swiper_award_hist_prev',
        },
        on: {
            resize: function () {
                swiper.changeDirection(getDirection());
            },

        },

    });




    function getDirection() {
        var windowWidth = window.innerWidth;
        var direction = window.innerWidth <= 0 ? 'vertical' : 'horizontal';

        return direction;
    }
});

