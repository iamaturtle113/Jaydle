#In my environment, worked correctly.

############################################

#=============== UPDATING YOUTUBE-DL CHANGED PATH TO /usr/local/bin from /usr/bin/

#License alongs with open source guide line.
#Masataka Nakamura (i_am_a_turtle_yh@yahoo.co.jp)

#----------FOR LINUX OS-------------------#

#Paste these code to your .bashrc

#--------Commands list--------#

#Loop (take multi arguments)
#Loop_num (take multi arguments)
#Playlist (take one argument)
#Playlist_num (take one argument)
#SearchDl (take multi arguments)
#SearchDl_num (take multi arguments)

#--------Examples--------------#
# Loop url1 url2 url3 => download from url1~3 simultaneously
# Playlist_num "playlist url" => download from playlist with numbering
# SearchDl_num earth environment fracking => download the search result of "earth environment fracking" with numbering

#----------YOUTUBE_DL------------------#
#You need to install youtube-dl to /usr/local/bin/youtube-dl
#and Firefox to /Applications/Firefox.app

################
n=5 # The Number of Simultaneous Download
################

####### Fundamental function, take plural argments and download simultaneously.

function Loop {
echo $#
for i in $*; do
#a=$(date | awk '{ print $4 }')_ydt.txt # Make file for redirect output from youtube-dl
if [ $(( `ps -a | grep youtube-dl | wc -l` )) -lt "$n" ]; then # If downloading files are less than the number of simultaneous download
/usr/local/bin/youtube-dl --no-playlist -f 18 "$i" -o '%(title)s.%(ext)s' > /dev/null & #option, no-playlist, file size, file title
else
while [ $(( `ps -a | grep youtube-dl | wc -l` )) -ge "$n" ]; do # If downloading more than max download
sleep 10
done
fi
done
wait &
#rm *_ydt.txt
}

####### Numbering version of Loop.

function Loop_num {
declare -i f; # f for file numbering
f=1 ;
echo $#
for i in $*; do
#a=$(date | awk '{ print $4 }')_ydt.txt
if [ $(( `ps -a | grep youtube-dl | wc -l` )) -lt "$n" ]; then
/usr/local/bin/youtube-dl --no-playlist -f 18 "$i" -o "$f"'_%(title)s.%(ext)s' > /dev/null &
f+=1;
else
while [ $(( `ps -a | grep youtube-dl | wc -l` )) -ge "$n" ]; do
sleep 10
done
fi
done
wait &
rm *_ydt.txt
}

function Loop_num_audio {
declare -i f; # f for file numbering
f=1 ;
echo $#
for i in $*; do
#a=$(date | awk '{ print $4 }')_ydt.txt
if [ $(( `ps -a | grep youtube-dl | wc -l` )) -lt "$n" ]; then
/usr/local/bin/youtube-dl --no-playlist --extract-audio --audio-format mp3 "$i" -o '%(title)s.%(ext)s' > /dev/null &
f+=1;
else
while [ $(( `ps -a | grep youtube-dl | wc -l` )) -ge "$n" ]; do
sleep 10
done
fi
done
wait &
rm *_ydt.txt
}

####### Download from playlist url using Loop. Take one argument.

function Playlist {

function LoopPlaylist {
html=html_last
wget -O $html $1
wait
cat $html | grep "<a href=\"/watch?v=" > html_source
cat html_source | sed -e 's;.*"/watch;watch;' -e 's/" .*//' -e 's;^;http://www.youtube.com/;' | awk -F\n -v ORS=' ' '{print}' > $html

Loop $(cat $html)
wait
rm html_source
rm $html
}

mkdir v
LoopPlaylist $1 &
}

####### Almost same to Playlist, numbering version.

function Playlist_num {

function LoopPlaylist_num {
html=html_last
wget -O $html $1
wait
cat $html | grep "<a href=\"/watch?v=" > html_source
cat html_source | sed -e 's;.*"/watch;watch;' -e 's/" .*//' -e 's;^;http://www.youtube.com/;' | awk -F\n -v ORS=' ' '{print}' > $html

##### Difference
Loop_num $(cat $html)
#####

wait
rm html_source
rm $html
}

mkdir v

#### Difference
LoopPlaylist_num $1 &
####
}
function Playlist_num_audio {

function LoopPlaylist_num {
html=html_last
wget -O $html $1
wait
cat $html | grep "<a href=\"/watch?v=" > html_source
cat html_source | sed -e 's;.*"/watch;watch;' -e 's/" .*//' -e 's;^;http://www.youtube.com/;' | awk -F\n -v ORS=' ' '{print}' > $html

##### Difference
Loop_num_audio $(cat $html)
#####

wait
rm html_source
rm $html
}

mkdir v

#### Difference
LoopPlaylist_num $1 &
####
}

#######Make argument for url search query

function Makeurl() {
for i in $@; do
arg="$arg""$i"+
done

arg="${arg%+}"
echo "$arg"
}

#######Pass arguments to search query and download

function SearchDl {
arg="" # Clean past variable of $arg

Makeurl $@

#open -a "/Applications/Firefox.app" "https://www.youtube.com/results?search_query=$arg"
firefox "https://www.youtube.com/results?search_query=$arg"

echo "https://www.youtube.com/results?search_query="$arg""
url="https://www.youtube.com/results?search_query="$arg""
echo "Downloading"

mkdir "$arg"
cd "$arg"
Playlist $url &
cd ../
mkdir v
}

####### Almost same to SearchDl

function SearchDl_num {

arg=""
Makeurl $@

#open -a "/Applications/Firefox.app" "https://www.youtube.com/results?search_query=$arg"
firefox "https://www.youtube.com/results?search_query=$arg"
echo "https://www.youtube.com/results?search_query="$arg""
url="https://www.youtube.com/results?search_query="$arg""
echo "Downloading"

mkdir "$arg"
cd "$arg"

#### Only difference
Playlist_num $url &
####
cd ../
mkdir v
}


function Loopa {

				#personal custom
				cd ~/ydlAudio
echo $#
for i in $*; do
#a=$(date | awk '{ print $4 }')_ydt.txt # Make file for redirect output from youtube-dl
if [ $(( `ps -a | grep youtube-dl | wc -l` )) -lt "$n" ]; then # If downloading files are less than the number of simultaneous download
/usr/local/bin/youtube-dl --no-playlist --extract-audio --audio-format mp3 "$i" -o '%(title)s.%(ext)s' > /dev/null & #option, no-playlist, file size, file title
else
while [ $(( `ps -a | grep youtube-dl | wc -l` )) -ge "$n" ]; do # If downloading more than max download
sleep 10
done
fi
done
wait &
#rm *_ydt.txt
cd -
}

