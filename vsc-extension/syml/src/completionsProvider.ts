import {
    CompletionItem,
    CompletionItemProvider,
    TextDocument,
    Position,
    CancellationToken,
    Range,
    CompletionItemKind
} from 'vscode';

export class EventCompletionsProvider implements CompletionItemProvider {

    events: any

    constructor(events: any) {
        this.events = events;
    }


    provideCompletionItems(
        document: TextDocument,
        position: Position,
        token: CancellationToken
    ): Promise<CompletionItem[]> {
        return new Promise((resolve, reject) => {
            const line = document.lineAt(position).text;
            if (line.includes('event')) {
                return resolve(this.getEventsProperties(document, position));
            }

            return resolve(this.getEventsNames());
        });
    }

    getEventsProperties(document: TextDocument, position: Position) {

        const res: CompletionItem[] = [];
        const eventName = this.findCurrentEventName(document, position);
        const property = this.findCurrentProperty(document,position);
        const event = this.events.find(event => event.name === eventName);
        const fields = event.fields;
        for (let field in fields) {
            if(!field.includes(property))
            {
              continue;
            }

            let name = field.replace(property, '');
            if(name.startsWith('.'))
            {
                name = name.substring(1);
            }
            res.push(new CompletionItem(name, CompletionItemKind.Field))
        }
        return res
    }

    findCurrentProperty(document: TextDocument, position: Position) 
    {
        const line = document.lineAt(position);
        const range: Range = new Range(line.range.start, position);
        const textBeforePosition = document.getText(range);
        const eventPattern = /event\.[a-zA-Z.]+(?!.*event\.)/;

        let match = eventPattern.exec(textBeforePosition);
        if(match == null)
        {
            return "event";
        }
        let res = match[0];
        return res;
    }


    findCurrentEventName(document: TextDocument, position: Position) {
        const range: Range = new Range(new Position(0, 0), position);
        const textBeforePosition = document.getText(range);
        const keywordIndex = textBeforePosition.lastIndexOf('event');
        if (keywordIndex === -1) {
            return null;  // 'event' keyword not found
        }

        const textBeforeKeyword = textBeforePosition.substring(0, keywordIndex);
        const patternMatches = textBeforeKeyword.match(/(\w+):\s*/g);
        if (!patternMatches || patternMatches.length === 0) {
            return null;  // No [Name]: pattern found
        }

        // Get the last [Name]: pattern before the keyword
        let lastPattern = patternMatches[patternMatches.length - 1];

        lastPattern = lastPattern.replace(": ", "").trim();
        return lastPattern;
    }

    getEventsNames() {
        const res: CompletionItem[] = [];
        for (let eventData of this.events) {

            res.push(new CompletionItem(eventData.name + ": \n       ", CompletionItemKind.Method))
        }
        return res;
    }

}