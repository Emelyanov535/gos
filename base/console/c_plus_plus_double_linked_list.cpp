#include <iostream>
#include <list>
#include <vector>
#include <string>

struct Person {
	std::string name;
	int age;
};

struct Node {
	Person data;
	Node* next;
	Node* prev;
};

Node* head = nullptr;

void printList() {
	Node* current = head;
	while (current != nullptr) {
		std::cout << "Name: " << current->data.name << ", Age: " << current->data.age << std::endl;
		current = current->next;
	}
}

void insertOrdered(Person person) {
	Node* newNode = new Node{ person, nullptr, nullptr };

	if (head == nullptr) {
		head = newNode;
		return;
	}

	Node* current = head;

	while (current != nullptr && current->data.age < person.age) {
		current = current->next;
	}

	if (current == head) {
		newNode->next = head;
		newNode->prev = nullptr;

		head->prev = newNode;
		head = newNode;
	}
	else if (current == nullptr) {
		Node* tail = head;
		while (tail->next != nullptr) {
			tail = tail->next;
		}
		tail->next = newNode;
		newNode->prev = tail;
	}
	else {
		Node* prevNode = current->prev;
		prevNode->prev = newNode;
		newNode->prev = prevNode;

		newNode->next = current;
		current->prev = newNode;
	}
}

void freeList() {
	Node* current = head;
	while (current != nullptr) {
		Node* next = current->next;
		delete current;
		current = next;
	}
}

int main() {
    std::string name;
    int age;

    std::cout << "Enter name and age. Type 'exit' to stop.\n";

    while (true) {
        std::cout << "Name: ";
        std::getline(std::cin, name);

        if (name == "exit") {
            break;
        }

        std::cout << "Age: ";
        std::cin >> age;
        std::cin.ignore();

        Person p = { name, age };
        insertOrdered(p);
    }

    std::cout << "\nList:\n";
    printList();

    return 0;
}