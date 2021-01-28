import { BetaReaderCommentDto } from "./BetaReaderCommentDto";

export interface BookDto {
    id: string;
    title: string;
    authorsName: number;
    genre: number;
    isPublished: boolean;
    processInstanceId: string;
    cityCountry: string;
    handwritePath: string;
    betaReadersComments: BetaReaderCommentDto[];

    jurisdiction: string;
}
