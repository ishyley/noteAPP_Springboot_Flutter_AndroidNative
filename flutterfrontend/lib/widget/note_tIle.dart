// ignore: file_names
import 'package:flutter/material.dart';
import 'package:noteapp/widget/custom_text.dart';

class Notetile extends StatelessWidget {
  final String topicText;
  final String bodyText;
  const Notetile({super.key, required this.topicText, required this.bodyText});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(18),
          color: Colors.white,
          border: Border.all(
              strokeAlign: BorderSide.strokeAlignOutside, color: Colors.brown)),
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: CustomText(
              text: topicText,
              fontSize: 28,
              fontWeight: FontWeight.bold,
              overflow: TextOverflow.ellipsis,
            ),
          ),
          const SizedBox(
            height: 5,
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: CustomText(
              text: bodyText,
              overflow: TextOverflow.ellipsis,
            ),
          ),
        ],
      ),
    );
  }
}
