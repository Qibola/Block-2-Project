// Reaction time

const targetNode = document.querySelector('.css-saet2v');
const targetNodePosition = targetNode.getBoundingClientRect();

const event = new MouseEvent('mousedown', {
    'view': window,
    'bubbles': true,
    'cancelable': true,
    'screenX': targetNodePosition.left,
    'screenY': targetNodePosition.top
});

const callback = function(mutationsList, observer) {
	if (targetNode.classList.contains("view-go")) {
		targetNode.dispatchEvent(event)
	}
};

const observer = new MutationObserver(callback);

const config = { attributes: true, attributeFilter: ['class'], childList: false, subtree: true };

observer.observe(targetNode, config);


// Human benchmark typing


const targetNode = document.querySelector('.letters');
setTimeout(() => {
	targetNode.dispatchEvent(new KeyboardEvent('keydown',{'key':'j'}))
	console.log("test")
	}
	, 2000);

const targetNode = document.querySelector('.letters');
const targetNodePosition = targetNode.getBoundingClientRect();

const event = new KeyboardEvent('keydown', {
    'view': window,
    'bubbles': true,
    'cancelable': true,
    'screenX': targetNodePosition.left,
    'screenY': targetNodePosition.top
});

targetNode.dispatchEvent(new Event('focus'));
targetNode.dispatchEvent(event)

const letters = document.querySelectorAll(".incomplete")

// Human benchmark big number


const targetNode = document.querySelector('.number-memory-test');

let bigNumber;

const callback = function(mutationsList, observer) {
	if (targetNode.classList.contains("question")) {
		bigNumber = document.querySelector(".big-number").innerHTML
	} else if (targetNode.classList.contains("prompt")) {
		console.log(bigNumber)
	}
};

const observer = new MutationObserver(callback);

const config = { attributes: true, attributeFilter: ['class'], childList: false, subtree: true };

observer.observe(targetNode, config);


// Verbal memory

let memory = []

const targetNode = document.querySelector(".word");

const buttons = document.querySelectorAll(".css-1qvtbrk button")

const seenButton = buttons[0];
const newButton = buttons[1];

let word

setInterval(() => {
word = targetNode.innerHTML

if (memory.includes(word)) {
	seenButton.click();
} else {
	memory.push(word);
	newButton.click();
}

}, 1)


// Aim



const targetNode = document.querySelector('.css-21ob1n').children[0].children[0].children[0];
const targetNodePosition = targetNode.getBoundingClientRect();

const event = new MouseEvent('mousedown', {
    'view': window,
    'bubbles': true,
    'cancelable': true,
    'screenX': targetNodePosition.left,
    'screenY': targetNodePosition.top
});

const callback = function(mutationsList, observer) {
	if (targetNode.classList.contains("view-go")) {
		targetNode.dispatchEvent(event)
	}
};

const observer = new MutationObserver(callback);

const config = { attributes: true, attributeFilter: ['class'], childList: true, subtree: true };

observer.observe(targetNode, config);

// Chimpanzee test


setInterval(() => {
elements = []

document.querySelectorAll(".css-19b5rdt").forEach(item => {
	const value = item.children[0].innerHTML - 1;
	elements[value] = item
})

elements.forEach(targetNode => {
	targetNode.click();
})

document.querySelector(".css-de05nr").click()
}, 1)


// Sequence


const targetNode = document.querySelector('.squares');
const targetNodePosition = targetNode.getBoundingClientRect();

let sequence = [];
let timeoutHandle;

const callback = function(mutations, observer) {
	mutations.forEach(function(mutation) {
		if (mutation.target.classList.contains("active")) {
			sequence.push(mutation.target);
			window.clearTimeout(timeoutHandle);
			timeoutHandle = window.setTimeout(() => {
			
				sequence.forEach(square => {
					const targetNode = square;

					const targetNodePosition = targetNode.getBoundingClientRect();

					const event = new MouseEvent('mousedown', {
						'view': window,
						'bubbles': true,
						'cancelable': true,
						'screenX': targetNodePosition.left,
						'screenY': targetNodePosition.top
					});
					targetNode.dispatchEvent(event)
				});
				console.log("test");
				sequence = [];
			}, 800)
				
		}
	});
};

const observer = new MutationObserver(callback);

const config = { attributes: true, attributeFilter: ['class'], childList: true, subtree: true };

observer.observe(targetNode, config);

