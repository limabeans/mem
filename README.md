mem v0.5
===

<b>3BLD = solving the 3x3x3 Rubik's Cube blindfolded.</b>

The first commutator and memory training application to exist, tailored to those who do 3BLD competitively. 

<b>Written in Java by Angel Lim.</b>

Currently, the program can generate scrambles with any combination of parity, flipped edges, and twisted corners, along with the choice of one forced edge or corner commutator.

I am a BLDer, inspired by the ideas of <i>deliberative practice</i>, which lead me to create this.

<b>It's still a work in progress, so here's how build/run/test/contribute:</b>

<code>git clone https://github.com/limabeans/mem.git</code><br>
<code>cd mem/source</code><br>
<code>javac -d ../classes *.java</code><br>
<code> cd ../classes</code><br>
<code>java GUI</code>

<b>Future features include:</b><br>
-generating scrambles of a certain metric (i.e. 13/7) <i>currently works, just no GUI implementation yet!</i><br>
-an implemented "Solve Stats" section<br>
-more sophisticated text exporting<br>
-memory training<br>
-generalization to include all buffers<br>
-a txt config file, for custom letter schemes and misc. config stuff!<br>
-the first timer with a built in Facebook chat?!<br>
-the first timer with a built in music playlist builder?!<br>

<b>Notes:</b><br>
<b>My buffers, </b><b>Edges: </b>DF, <b>Corners: </b>UBL<br>
You may noticed I hard-encoded my own lettering scheme for now.
This was just to simplify coding on my part, this is easily changeable later.<br>
<b>For now, to test the "Force edge/corner comm" feature(s), follow the following directions:</b><br>
1. type in <code>BA</code> into "Force corner comm" and hit [ENTER]<br>
2. scramble using the given scramble <b>in your memo/solving orientation</b><br>
3. <code>BA</code> is my letter pair for <code>UBL-UBR-UFR</code>, so you are guaranteed a <code>CCW A-perm</code> in your next BLD solve!

In the meantime, obfuscation working as intended. :^)

<b>Screenshot:</b>
![ScreenShot](https://raw.github.com/limabeans/mem/master/screenshots_v0.5/screenshot2.png)



