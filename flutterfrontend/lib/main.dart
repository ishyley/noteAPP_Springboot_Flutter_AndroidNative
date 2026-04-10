import 'package:flutter/material.dart';
import 'package:noteapp/repo/database.dart';
import 'package:noteapp/screens/home_screen.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
 // Database? database;
  MyApp({super.key, });

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData.dark(useMaterial3: true),
      home: HomePage(),
    );
  }
}
