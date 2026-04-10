import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:noteapp/constant.dart';
import 'package:noteapp/models/note.dart';
import 'package:noteapp/repo/database.dart';
import 'package:noteapp/screens/home_screen.dart';
import 'package:noteapp/widget/custom_snackbar.dart';
import 'package:noteapp/widget/custom_textfield.dart';

class NoteInputScreen extends StatefulWidget {
  const NoteInputScreen({
    super.key,
  });

  @override
  State<NoteInputScreen> createState() => _NoteInputScreenState();
}

class _NoteInputScreenState extends State<NoteInputScreen> {
  TextEditingController titlecontroller = TextEditingController();
  TextEditingController noteController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    Future<void> saveNote() async {
      if (titlecontroller.text.isEmpty && noteController.text.isEmpty) {
        showSnackbar(
          context,
          'Empty note Discarded',
        );
      } else {
      //  Database.notelist.add(Note(titlecontroller.text, noteController.text));
      }
      log('${titlecontroller.text}, ${noteController.text}');
    }

    return PopScope(
      canPop: true,
      onPopInvokedWithResult: (bool didPop, dynamic result) async {
        if (didPop) {
          await saveNote();
          Navigator.push(context, MaterialPageRoute(builder: (_) => HomePage()))
              .then((_) => setState(() {}));
        } else {
          await saveNote();
        }
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: editpageColor,
          automaticallyImplyLeading: false,
          leading: Padding(
            padding: const EdgeInsets.all(8.0),
            child: GestureDetector(
              onTap: () {
                saveNote();
                Navigator.push(
                        context, MaterialPageRoute(builder: (_) => HomePage()))
                    .then((_) => setState(() {}));
              },
              child: Container(
                height: 150,
                width: 100,
                decoration: BoxDecoration(
                    shape: BoxShape.circle, color: editpageButtonColor),
                child: const Icon(
                  Icons.keyboard_arrow_left,
                  color: Colors.white,
                  size: 20,
                ),
              ),
            ),
          ),
          actions: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                height: 50,
                width: 50,
                decoration: BoxDecoration(
                    shape: BoxShape.circle, color: editpageButtonColor),
                child: const Icon(
                  Icons.menu,
                  color: Colors.white,
                  size: 20,
                ),
              ),
            )
          ],
        ),
        backgroundColor: editpageColor,
        body: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(16),
            child: Column(
              children: [
                CustomTextfield(
                  controller: titlecontroller,
                  showCursor: true,
                  hintText: 'Title',
                  cursorHeight: 60,
                  fontSize: 32,
                  fontWeight: FontWeight.bold,
                  hintfontSize: 32,
                  hintfontWeight: FontWeight.bold,
                ),
                CustomTextfield(
                  controller: noteController,
                  showCursor: true,
                  hintText: 'Note',
                  fontSize: 18,
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
