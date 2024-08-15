import { Food } from "./food";
export type Restaurant = {
    emailId?: string;
    password?: string;
    name?: string;
    address?: string;
    role?: string;
    foodList?: Food[];

}