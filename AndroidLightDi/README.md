# Android quiz sample using LightDi

Simple quiz program using dependency injection.
Also includes Room persistence library.

Some highlights:

 - `CommonActivityBase`: This class is the entry point to process LightDi annotations
 - `MainActivity`: Loading list of beans by interface (ex. calling method on bean on startup)
 - `DiContextHolder`: holds lazy loaded LightDiContext
 - Going "under" activities, everything is handled by the LightDi context