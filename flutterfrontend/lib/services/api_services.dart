import 'dart:convert';
import 'dart:developer';

import 'package:noteapp/assets/app_values.dart';
import 'package:http/http.dart' as http;
import 'package:noteapp/models/note.dart';

class ApiServices {
  static final http.Client client = http.Client();
  // Future<void> fetchNote() async {
  //   const String url = '${AppValues.baseUrl}notes';

  //   try {
  //     final response = await http.get(Uri.parse(url));
  //     if (response.statusCode == 200) {
  //       var data = jsonDecode(response.body);
  //       log(data);
  //     }
  //   } catch (e) {
  //     log(e.toString());
  //   }
  // }

  static Future<List<Note>?> fetchAllNote() async {
    try {
      final response = await client.get(Uri.parse('http://localhost:8080/notes'));
      if (response.statusCode == 200) {
        final List result = json.decode(response.body);
        log("api called");
        print(result);
        return result.map((e) => Note.fromJson(e)).toList();
      }
    } catch (e) {
      print(e.toString());
    }
    return null;
  }
}
