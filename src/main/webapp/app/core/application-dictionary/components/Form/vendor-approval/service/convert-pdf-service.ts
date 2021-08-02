import {jsPDF} from 'jspdf';


export default class ConvertPdfService{

    private posX: number= 20;
    private posY: number= 20;

    doc: jsPDF;

    constructor(){
        this.doc= new jsPDF();
    } 

    public printPdf(header: string[], data: {[key: string]: string}[], filename: string): void{

        let dateNow= Date.now();
        this.doc.table(this.posX, this.posY, data, this.createHeaders(header), { autoSize : true});
        this.doc.setProperties({ title: filename + `${dateNow}` });
        this.doc.output('dataurlnewwindow');
    }

    private createHeaders(header: string[]): any[] {
        let res= new Array();
        header.forEach(h=> {
            res.push({
                id: h,
                name: h,
                prompt: h,
                width: 65,
                align: "center",
                padding: 0
              });
        })

        return res;
    }
}