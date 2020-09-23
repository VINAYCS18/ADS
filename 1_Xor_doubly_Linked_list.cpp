#include<iostream>

using namespace std;

struct Node{
    public:
        int data;
        Node* next;
};

Node* Xor(Node* a, Node* b){
    return (Node*) ((uintptr_t) (a) ^ (uintptr_t) (b));
}

void insert_front(Node** head, int val){
    if(*head == NULL){
        *head = new Node();
        (*head)->data = val;
        return;
    }

    Node* newnode = new Node();
    newnode->data = val;
    newnode->next = (*head);

    (*head)->next = Xor(newnode, (*head)->next);
    (*head) = newnode;
}

void insert_Atend(Node** head, int val){
    if(*head == NULL){
        *head = new Node();
        (*head)->data = val;
        return;
    }

    Node* newnode = new Node();
    newnode->data = val;

    Node* curr = *head;
    Node* prev = NULL;
    Node* next = Xor(prev, curr->next);
    while (next != NULL)
    {
        prev = curr;
        curr = next;
        next = Xor(prev, curr->next);
    }

    newnode->next = curr;
    curr->next = Xor(prev, newnode);
}

void printList(Node* head){
    Node *curr = head;
    Node *prev = NULL;
    Node *next;

    cout << "Following are the nodes of Linked List: \n";

    while (curr != NULL)
    {
        cout<<curr->data<< endl;
        next = Xor(prev, curr->next);
        prev = curr;
        curr = next;
    }
}

int main(){
    Node *head = NULL;
    insert_front(&head, 10);
    insert_front(&head, 20);
    insert_front(&head, 30);
    insert_front(&head, 40);
    printList(head);
    return 0;
}
