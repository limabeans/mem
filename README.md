mem
===

Commutator and memory training application for those who do 3BLD competitively. 

Written in Java by Angel Lim.

<b>3BLD = solving the 3x3x3 Rubik's Cube blindfolded.</b>

Currently, the program can generate scrambles with any combination of parity, flipped edges, and twisted corners, along with the choice of one forced edge or corner commutator.
I am a BLDer, inspired by the ideas of link:http://en.wikipedia.org/wiki/Practice_%28learning_method%29[deliberative practice], which lead me to create this.

<b>Future features include:</b><br>
-generating scrambles of a certain metric (i.e. 13/7)<br>
-an implemented "Solve Stats" section<br>
-more sophisticated text exporting<br>
-memory training<br>
-a txt config file<br>
-the first timer with a built in Facebook chat?!<br>
-the first timer with a built in music playlist builder?!<br>

<b>Here's how build/run/test/contribute:</b><br>

<code>cd ./source</code><br>
<code>javac -d ../classes *.java</code><br>
<code> cd ../classes</code><br>
<code>java GUI</code>


You may noticed I hard-encoded my own lettering scheme for now.
This was just to simplify coding on my part, this is easily changeable later.
In the meantime, obfuscation working as intended. :^)

