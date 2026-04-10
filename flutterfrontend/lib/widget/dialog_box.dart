import 'package:flutter/material.dart';

class DialogBox extends StatelessWidget {
  const DialogBox({super.key});

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      actions: [],
      content: Column(
        children: [
          Row(
            children: [
              Container(
                decoration: const BoxDecoration(shape: BoxShape.circle),
                child: const Icon(Icons.keyboard_arrow_left),
              ),
              Container(
                decoration: const BoxDecoration(shape: BoxShape.circle),
                child: const Icon(Icons.menu),
              )
            ],
          )
        ],
      ),
    );
  }
}
