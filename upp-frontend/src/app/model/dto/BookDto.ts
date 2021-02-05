import { BetaReaderCommentDto } from "./BetaReaderCommentDto";

export interface BookDto {
    id: string;
    title: string;
    authorsName: string;
    genre: string;
    isPublished: boolean;
    processInstanceId: string;
    cityCountry: string;
    handwritePath: string;
    betaReadersComments: BetaReaderCommentDto[];
    isPlagiarized: boolean;
    isRejected: boolean;

    jurisdiction: string;
}
