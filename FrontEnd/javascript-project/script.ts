document.addEventListener("DOMContentLoaded", () => {
    const fetchBtn = document.querySelector<HTMLButtonElement>("#fetchBtn")
    const img = document.querySelector<HTMLImageElement>("#displayImage")
    const errorDiv = document.querySelector<HTMLDivElement>("#error");

    if (!fetchBtn || !img || !errorDiv) {
        return;
    }

    type DogApiResponse = {
        message: string;
    };

    const url : string = "https://dog.ceo/api/breeds/image/random";
    fetchBtn.addEventListener("click", async () => {
        await fetchDogImage(url)


});
    const fetchDogImage = async (url: string) => {
        try {
            const response = await fetch(url);
            if (!response.ok){
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data: DogApiResponse = await response.json();

            img.src = data.message;
        }
        catch (error) {
            if (error instanceof Error) {
                errorDiv.textContent = `Error fetching dog image: ${error.message}`;
            }
        }
    };

});