# torc-gui
Graphical user interface endpoint for torc

## Installation

You need to install torc (https://github.com/rascal999/torc) in order to
use this tool. It is recommended to use the torc docker image available at
https://hub.docker.com/r/rascal999/torc/.

#### From git (using Apache Ant)

You can use Apache Ant to run torc-gui like so:

1. ``git clone https://github.com/rascal999/torc-gui``
2. ``cd torc-gui``
3. ``ant run``

## Usage

Should be fairly self-explanatory. You have three tabs, 'Run Tool',
'Run Assessment', and 'Jobs'. The 'Run Tool' tab allows you to specify
the tool and options you'd like to configure against a particular target.
The 'Run Assessment' tab allows you to specify the options for an
assessment (which is a collection of tools). The 'Jobs' tab allows you to
see jobs which have been executed and have completed execution. You can
export tool results from here and save them to your disk.

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## Credits

Developed by Aidan Marlin (aidan [dot] marlin [at] gmail [dot] com).
=======
