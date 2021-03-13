// ==UserScript==
// @name         Reddit Blur Remover
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  Remove spoilers
// @author       tbtuan
// @include      https://www.reddit.com/r/*
// @grant        none
// ==/UserScript==

(function() {
    'use strict';

    // Your code here...
    // Reddit remove blur
    let callback = (entries, observer) => {
        entries.forEach((entry) => {
            if (entry.intersectionRatio > 0) {
                const element = entry.target;
                console.log(entry.target);
                observer.observe(element.nextSibling);
                const postimg = element.querySelector("img[alt='Post image']");
                const isVideo = element.querySelector(".vLH0XV-l8Y4mNGUvw4HHy");
                console.log(isVideo);
                if (!postimg || !postimg.getAttribute("src").includes("blur") || isVideo) {
                    observer.unobserve(entry.target);
                    return;
                }
                const imgSrc = element.querySelector("._13svhQIUZqD9PVzFcLwOKT");
                //console.log(postimg);
                if (imgSrc) {
                    //console.log(imgSrc.getAttribute("href"));
                    // ends with an image .png, .jpg, ...
                    const href = imgSrc.getAttribute("href");
                    //console.log(href[href.length - 4]);
                    if (href[href.length - 4] != ".") {
                        // if imgur then https://imgur.com/a/CRIexYG => https://i.imgur.com/ctyFaGA.png
                        console.log(href + ".png");
                        postimg.setAttribute("src", href + ".png");
                    } else {
                        postimg.setAttribute("src", href);
                    }
                    postimg.classList.remove("_2_tDEnGMLxpM6uOa2kaDB3");
                    postimg.classList.remove("_3oBPn1sFwq76ZAxXgwRhhn");
                } else {
                    if (postimg) {
                        const regex = /.*?preview\.([^\?]+)/i;
                        const result = postimg.getAttribute("src").match(regex);
                        if (result) {
                            postimg.setAttribute("src", `https://i.${result[1]}`);
                            postimg.classList.remove("_2_tDEnGMLxpM6uOa2kaDB3");
                            postimg.classList.remove("_3oBPn1sFwq76ZAxXgwRhhn");
                        }
                    }
                }
                observer.unobserve(entry.target);
            }
        });
    };

    let observer = new IntersectionObserver(callback, {
        rootMargin: "0px",
        threshold: 1.0,
    });

    // First div (Post) inside the .rpBJOHq2PR60pnwJlUyP0 container
    observer.observe(document.querySelector(".rpBJOHq2PR60pnwJlUyP0 div"));
})();