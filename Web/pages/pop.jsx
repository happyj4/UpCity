import { use, useState } from "react";

export default function List() {
  const [passport, setPassport] = useState("");
  const [phone, setPhone] = useState("");
  const [day, setDay] = useState("");
  const [month, setMonth] = useState("");
  const [year, setYear] = useState("");
  const [text, setText] = useState("");
  const [fixGrammar, setFixGrammar] = useState("");
  const [email, setEmail] = useState("");
  const [html, setHtml] = useState("");
  const [textHTML, setTextHTML] = useState("");
  const [name, setName] = useState("");
  const [users, setUsers] = useState([])

  const handleOkClick = async () => {
    try {
      const res = await fetch(
        "https://22b1-46-150-17-217.ngrok-free.app/check-password",
        {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: new URLSearchParams({ password: passport }),
        },
      );

      const data = await res.json();
      alert(data.message);
    } catch (error) {
      alert("Помилка з'єднання з сервером (пароль)");
    }
  };

  const phoneOnClick = async () => {
    try {
      const res = await fetch(
        "https://22b1-46-150-17-217.ngrok-free.app/check-phone",
        {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: new URLSearchParams({ phone }),
        },
      );

      const data = await res.json();
      alert(data.message);
    } catch (error) {
      alert("Помилка з'єднання з сервером (телефон)");
    }
  };

  const dateOnClick = async () => {
  try {
    const query = new URLSearchParams({ day, month, year }).toString();
    const res = await fetch(
      `https://22b1-46-150-17-217.ngrok-free.app/weekday?${query}`,
      {
        headers: {
          Accept: "application/json",
          "ngrok-skip-browser-warning": "true",
        },
      }
    );

    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(`Сервер відповів з кодом ${res.status}: ${errorText}`);
    }

    const data = await res.json();
    alert(data.weekday)
  } catch (error) {
    console.error("Помилка з'єднання з сервером (дата):", error.message);
    alert("Помилка: " + error.message);
  }
};

  const grammarOnClick = async () => {
    try {
      const res = await fetch(
        "https://22b1-46-150-17-217.ngrok-free.app/check",
        {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ text }),
        },
      );

      const data = await res.json();
      console.log(data);
      alert(data.errors[0].error ? data.errors[0].error : "Все добре");
    } catch (error) {
      alert("Помилка з'єднання з сервером (граматика)");
    }
  };

  const fixingGrammar = async () => {
    try {
      const res = await fetch("https://22b1-46-150-17-217.ngrok-free.app/fix", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ text: fixGrammar }),
      });

      const data = await res.json();
      console.log(data);
      alert(data.fixed_text ? data.fixed_text : "Все добре");
    } catch (error) {
      alert("Помилка з'єднання з сервером (граматика)");
    }
  };

  const checkDbStatus = async () => {
    try {
      const res = await fetch(
        "https://22b1-46-150-17-217.ngrok-free.app/db-status",
        {
          method: "GET",
          headers: {
            Accept: "application/json",
            "ngrok-skip-browser-warning": "true",
          },
        },
      );

      const text = await res.text();
      try {
        const data = JSON.parse(text);
        console.log(data);
        alert(`Статус БД: ${JSON.stringify(data)}`);
      } catch (e) {
        console.error("Не JSON:", text);
        alert("Сервер повернув неочікувану відповідь");
      }
    } catch (error) {
      alert("Помилка при отриманні статусу бази даних");
    }
  };

 const checkusers = async () => {
  try {
    const res = await fetch(
      "https://22b1-46-150-17-217.ngrok-free.app/users",
      {
        method: "GET",
        headers: {
          Accept: "application/json",
          "ngrok-skip-browser-warning": "true",
        },
      },
    );

    if (!res.ok) {
      // Якщо статус відповіді не успішний
      throw new Error(`HTTP помилка: ${res.status}`);
    }

    try {
      const data = await res.json(); // Парсинг JSON
      console.log(data);
    } catch (e) {
      console.error("Не JSON:", e.message);
      alert("Сервер повернув неочікувану відповідь");
    }
  } catch (error) {
    console.error("Помилка:", error.message);
    alert("Помилка при отриманні статусу бази даних");
  }
};


  const checkEmail = async () => {
    try {
      const res = await fetch(
        "https://22b1-46-150-17-217.ngrok-free.app/check_email",
        {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ email }),
        },
      );

      const data = await res.json();
      console.log(data);
      alert(data.message || "Перевірка пройшла успішно");
    } catch (error) {
      console.error("Помилка:", error);
      alert("Не вдалося перевірити email");
    }
  };

  const convertHtmlToText = async () => {
    try {
      const response = await fetch(
        "https://22b1-46-150-17-217.ngrok-free.app/html_to_text",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
          body: JSON.stringify({ text: html }),
        },
      );

      const data = await response.json();
      console.log(data);
      alert(data.result);
    } catch (error) {
      alert("Щось не так: " + error.message);
    }
  };

  const convertTextToHtml = async () => {
    try {
      const response = await fetch(
        "https://22b1-46-150-17-217.ngrok-free.app/text_to_html_paragraphs",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
          body: JSON.stringify({ text: textHTML }),
        },
      );

      const data = await response.json();
      console.log(data);
      alert(data.result);
    } catch (error) {
      alert("Щось не так: " + error.message);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(
        "https://22b1-46-150-17-217.ngrok-free.app/add_user",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
            "ngrok-skip-browser-warning": "true",
          },
          body: JSON.stringify({
            name: name,
            email: email,
          }),
        },
      );

      const result = await response.json();
      alert("Користувача додано успішно!\n" + JSON.stringify(result));
    } catch (error) {
      alert("Помилка при надсиланні: " + error.message);
    }
  };

  return (
    <div className="w-full max-w-md mx-auto mt-10 p-4 bg-white rounded-lg shadow-md space-y-6">
      {/* Пароль */}
      <div>
        <label
          htmlFor="password"
          className="block text-gray-700 text-sm font-semibold mb-2"
        >
          Введіть пароль
        </label>
        <input
          id="password"
          type="dfdsfdsds"
          value={passport}
          onChange={(e) => setPassport(e.target.value)}
          className="w-full border border-gray-300 rounded-md px-3 py-2 mb-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="******"
          required
        />
        <button
          type="button"
          onClick={handleOkClick}
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Перевірити пароль
        </button>
      </div>

      {/* Телефон */}
      <div>
        <label
          htmlFor="phone"
          className="block text-gray-700 text-sm font-semibold mb-2"
        >
          Номер телефону
        </label>
        <input
          id="phone"
          type="tel"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
          className="w-full border border-gray-300 rounded-md px-3 py-2 mb-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="+380997102399"
          required
        />
        <button
          type="button"
          onClick={phoneOnClick}
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Перевірити телефон
        </button>
      </div>

      {/* Дата народження */}
      <div>
        <label className="block text-gray-700 text-sm font-semibold mb-2">
          Введіть дату народження
        </label>
        <div className="flex space-x-2 mb-2">
          <input
            type="number"
            placeholder="День"
            value={day}
            onChange={(e) => setDay(e.target.value)}
            className="w-1/3 border border-gray-300 rounded-md px-3 py-2"
          />
          <input
            type="number"
            placeholder="Місяць"
            value={month}
            onChange={(e) => setMonth(e.target.value)}
            className="w-1/3 border border-gray-300 rounded-md px-3 py-2"
          />
          <input
            type="number"
            placeholder="Рік"
            value={year}
            onChange={(e) => setYear(e.target.value)}
            className="w-1/3 border border-gray-300 rounded-md px-3 py-2"
          />
        </div>
        <button
          type="button"
          onClick={dateOnClick}
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Визначити день тижня
        </button>
      </div>

      {/* Граматика */}
      <div>
        <label
          htmlFor="grammar"
          className="block text-gray-700 text-sm font-semibold mb-2"
        >
          Виправлення граматики
        </label>
        <input
          id="grammarfix"
          type="text"
          value={text}
          onChange={(e) => setText(e.target.value)}
          className="w-full border border-gray-300 rounded-md px-3 py-2 mb-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="Введіть речення українською"
          required
        />
        <button
          type="button"
          onClick={grammarOnClick}
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Перевірити граматику
        </button>
      </div>

      <div>
        <label
          htmlFor="grammar"
          className="block text-gray-700 text-sm font-semibold mb-2"
        >
          Введіть речення для перевірки граматики
        </label>
        <input
          id="grammarfix"
          type="text"
          value={fixGrammar}
          onChange={(e) => setFixGrammar(e.target.value)}
          className="w-full border border-gray-300 rounded-md px-3 py-2 mb-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="Введіть речення українською"
          required
        />
        <button
          type="button"
          onClick={fixingGrammar}
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Перевірити граматику
        </button>

        <button
          type="button"
          onClick={checkDbStatus}
          className="w-full mt-10 bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Створити БД
        </button>
      </div>

      <div>
        <label
          htmlFor="phone"
          className="block text-gray-700 text-sm font-semibold mb-2"
        >
          Email
        </label>
        <input
          id="email"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full border border-gray-300 rounded-md px-3 py-2 mb-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="kukulenko.denis@gmail.com"
          required
        />
        <button
          type="button"
          onClick={checkEmail}
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Перевірити пошту
        </button>
      </div>

      <div>
        <label
          htmlFor="phone"
          className="block text-gray-700 text-sm font-semibold mb-2"
        >
          HTML в текст
        </label>
        <input
          id="text"
          type="text"
          value={html}
          onChange={(e) => setHtml(e.target.value)}
          className="w-full border border-gray-300 rounded-md px-3 py-2 mb-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="HTML"
          required
        />
        <button
          type="button"
          onClick={convertHtmlToText}
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Перевірити пошту
        </button>
      </div>

      <div>
        <label
          htmlFor="phone"
          className="block text-gray-700 text-sm font-semibold mb-2"
        >
          Текст в HTML
        </label>
        <input
          id="text"
          type="text"
          value={textHTML}
          onChange={(e) => setTextHTML(e.target.value)}
          className="w-full border border-gray-300 rounded-md px-3 py-2 mb-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          placeholder="Текст"
          required
        />
        <button
          type="button"
          onClick={convertTextToHtml}
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Перевірити пошту
        </button>
      </div>

      <form onSubmit={handleSubmit} className="max-w-md mx-auto p-4">
        <div className="mb-4">
          <label
            htmlFor="name"
            className="block text-gray-700 text-sm font-semibold mb-2"
          >
            Ім’я
          </label>
          <input
            id="name"
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Введіть ім’я"
            required
          />
        </div>

        <div className="mb-4">
          <label
            htmlFor="email"
            className="block text-gray-700 text-sm font-semibold mb-2"
          >
            Email
          </label>
          <input
            id="email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="example@email.com"
            required
          />
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          Надіслати
        </button>
      </form>

      <div className="max-w-md mx-auto mt-6">
      <h2 className="text-lg font-bold mb-4">Список користувачів</h2>
      {users.length === 0 ? (
        <p>Немає користувачів.</p>
      ) : (
        <ul className="space-y-2">
          {users.map((user, index) => (
            <li key={index} className="p-3 border rounded shadow-sm">
              <p><strong>Ім’я:</strong> {user.name}</p>
              <p><strong>Email:</strong> {user.email}</p>
            </li>
          ))}
        </ul>
      )}
    </div>

    <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
          onClick={checkusers}
        >
          Показати нових користувачів
        </button>
    </div>
  );
}
