import 'package:flutter/material.dart';
import 'package:noteapp/Utils/tab_item.dart';
import 'package:noteapp/models/note.dart';
import 'package:noteapp/repo/database.dart';
import 'package:noteapp/services/api_services.dart';
import 'package:noteapp/widget/custom_text.dart';
import 'package:noteapp/widget/note_TIle.dart';
import 'package:noteapp/screens/note_input_screen.dart';

class HomePage extends StatefulWidget {
//  Database? database;
  HomePage({
    super.key,
    //  this.database,
  });

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List<Note>? note;
  var isLoading = false;
  // final notes = Database.notelist;

  getData() async {
    note = await ApiServices.fetchAllNote();
    if (note != null) {
      setState(() {
        isLoading = true;
      });
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    getData();
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
          floatingActionButton: FloatingActionButton(
            onPressed: () {
              Navigator.push(
                context,
                PageRouteBuilder(
                  pageBuilder: (context, animation, secondaryAnimation) =>
                      const NoteInputScreen(),
                  transitionsBuilder:
                      (context, animation, secondaryAnimation, child) {
                    const begin = Offset(1.0, 0.0);
                    const end = Offset.zero;
                    const curve = Curves.easeInOut;

                    var tween = Tween(begin: begin, end: end)
                        .chain(CurveTween(curve: curve));

                    return SlideTransition(
                      position: animation.drive(tween),
                      child: child,
                    );
                  },
                ),
              );
            },
            backgroundColor: Colors.white,
            child: const Icon(
              Icons.add,
              color: Colors.black,
            ),
          ),
          appBar: AppBar(
            automaticallyImplyLeading: false,
            title: const CustomText(
              text: 'Hi, Dev',
              color: Colors.white,
            ),
            leading: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                height: 150,
                width: 100,
                decoration: const BoxDecoration(
                    shape: BoxShape.circle,
                    color: Color.fromARGB(255, 153, 174, 212)),
                child: const Icon(
                  Icons.account_box,
                  color: Colors.white,
                  size: 20,
                ),
              ),
            ),
            actions: [
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Container(
                  height: 50,
                  width: 50,
                  decoration: const BoxDecoration(
                      shape: BoxShape.circle, color: Colors.black),
                  child: const Icon(
                    Icons.menu,
                    color: Colors.white,
                    size: 20,
                  ),
                ),
              )
            ],
            bottom: PreferredSize(
                preferredSize: const Size.fromHeight(80),
                child: TabBar(
                    indicatorSize: TabBarIndicatorSize.tab,
                    dividerColor: Colors.transparent,
                    indicator: const BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.all(Radius.circular(30))),
                    tabs: [
                      Container(
                          decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(30),
                              border: Border.all(color: Colors.white70)),
                          child: const TabItem(title: 'All', count: 6)),
                      Container(
                          decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(30),
                              border: Border.all(color: Colors.white70)),
                          child: const TabItem(title: 'Important', count: 1)),
                      Container(
                          decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(30),
                              border: Border.all(color: Colors.white70)),
                          child: const TabItem(title: 'Bookmarked', count: 10)),
                    ])),
          ),
          body: TabBarView(children: [
            Center(
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Column(
                  children: [
                    const CustomText(
                      text: 'My Notes',
                      fontSize: 32,
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    Expanded(
                      child: Visibility(
                        visible: isLoading,
                        replacement:
                            const Center(child: CircularProgressIndicator()),
                        child: GridView.builder(
                          itemCount: note?.length,
                          gridDelegate:
                              const SliverGridDelegateWithFixedCrossAxisCount(
                                  crossAxisCount: 2,
                                  crossAxisSpacing: 8,
                                  mainAxisSpacing: 8),
                          itemBuilder: (context, index) {
                            final notes = note![index];
                            return Notetile(
                                topicText: notes.content,
                                bodyText: notes.content);
                          },
                        ),
                      ),
                    )
                  ],
                ),
              ),
            ),
            const Center(
              child: CustomText(text: 'Important'),
            ),
            const Center(
              child: CustomText(text: 'Bookmarked'),
            ),
          ])),
    );
  }
}
