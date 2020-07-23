// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

function getmsg() {
  //into the getmsg function
  //fetching comments from servlet 
  fetch('/comments')
    .then(response => response.json())
    .then(comment_list => {
      const commentcontainer = document.getElementById('comment-container');
      comment_list.forEach((comment_) => {
        commentcontainer.appendChild(createCommElement(comment_));
      });

      //Comments are successfully added..
    });


}


/** Creates an element that represents a Commentobject*/
function createCommElement(comment_) {
  const comment_Element = document.createElement('li');
  comment_Element.className = 'comment_';
  const commentElement = document.createElement('span');
  commentElement.innerText = comment_.comment;
  comment_Element.appendChild(commentElement);
  return comment_Element;
}
