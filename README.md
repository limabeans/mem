mem
===

<b>3BLD = solving the 3x3x3 Rubik's Cube blindfolded.</b>

Commutator and memory training application for those who do 3BLD competitively. 

<b>Written in Java by Angel Lim.</b>

Currently, the program can generate scrambles with any combination of parity, flipped edges, and twisted corners, along with the choice of one forced edge or corner commutator.

I am a BLDer, inspired by the ideas of <i>deliberative practice</i>, which lead me to create this.

To see what it's looking like, download and double-click <code>mem.jar</code>. <b>This is simply the latest JAR file that I built, and I cannot guarantee it is the most up-to-date version. Instead, I recommend building the app yourself.</b>

<b>Here's how build/run/test/contribute:</b>

<code>git clone https://github.com/limabeans/mem.git</code><br>
<code>cd mem/source</code><br>
<code>javac -d ../classes *.java</code><br>
<code> cd ../classes</code><br>
<code>java GUI</code>

<b>Future features include:</b><br>
-generating scrambles of a certain metric (i.e. 13/7)<br>
-an implemented "Solve Stats" section<br>
-more sophisticated text exporting<br>
-memory training<br>
-a txt config file, for custom letter schemes and misc. config stuff!<br>
-the first timer with a built in Facebook chat?!<br>
-the first timer with a built in music playlist builder?!<br>

<b>Notes:</b>

You may noticed I hard-encoded my own lettering scheme for now.
This was just to simplify coding on my part, this is easily changeable later.
In the meantime, obfuscation working as intended. :^)


